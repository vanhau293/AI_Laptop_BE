package com.example.demo.service.impl;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import com.example.demo.bean.ImageUtils;
import com.example.demo.bean.APICallPython;
import com.example.demo.model.Brand;
import com.example.demo.model.Laptop;
import com.example.demo.model.request.LaptopRequest;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CPURepository;
import com.example.demo.repository.GPURepository;
import com.example.demo.repository.LabelRepository;
import com.example.demo.repository.LaptopRepository;
import com.example.demo.service.LaptopService;
import lombok.val;

@Service
public class LaptopServiceImpl implements LaptopService {
    private static final String URL_IMG="src/main/java/com/example/demo/image/";
    private static final double brand_max = 22;
    private static final double price_max = 129990001;
    private static final double cpu_max = 156;
    private static final double ram_max = 32;
    private static final double rom_max = 2048;
    private static final double widescreen_max = 17.3;
    private static final double gpu_max = 146;
    private static final double weight_max = 3.2;
    @Autowired
    private LaptopRepository laptopRepository;

    @Autowired
    private CPURepository cpuRepository;

    @Autowired
    private GPURepository gpuRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private APICallPython apiCallPython;

    @Override
    public ResponseEntity<?> findLaptop (String labelId, String demand) throws URISyntaxException {
        if ( demand.equals("") ) {
            if ( labelId.equals("3") ) {
                return ResponseEntity.ok(laptopRepository.findLaptop());
            }
            else {
                return ResponseEntity.ok(laptopRepository.findLaptopByLabel(Integer.parseInt(labelId)));
            }
        }
        else {
            List<Brand>         listBrand   = brandRepository.findAll();
            String brandId="";
            List<String> listDemand = List.of(demand.split(" "));
            for(String de: listDemand){
                for(Brand brand : listBrand){
                    if(de.toLowerCase(Locale.ROOT).equals(brand.getBrandName().toLowerCase(Locale.ROOT))){
                        brandId = brand.getBrandId().toString();
                        break;
                    }
                }
                if(!brandId.equals("")) break;
            }
            if(!brandId.equals("")){
                return ResponseEntity.ok(laptopRepository.findLaptopByBrand(Integer.parseInt(brandId)));
            }
            Map<String, String> textRequest = new HashMap();
            textRequest.put("info", demand);
            String    labelFromDemand = null;

            labelFromDemand = apiCallPython.localApiGetDemand().post()
                                    .uri("/demand")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromValue(textRequest))
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .block();
            int label;
            try {
                label = Integer.parseInt(labelFromDemand.replace("\"",""));
            }
            catch (NumberFormatException e) {
                label = 0;
            }
            return ResponseEntity.ok(laptopRepository.findLaptopByLabel(label));

        }
    }

    @Override
    public ResponseEntity<?> addLaptop (LaptopRequest laptopRequest) {
        Laptop laptop = laptopRepository.findLaptopByName(laptopRequest.getLaptopName());
        if ( laptop != null ) {
            return ResponseEntity.ok("Laptop đã tồn tại");
        }
        Laptop newLaptop = new Laptop();
        newLaptop.setLaptopName(laptopRequest.getLaptopName());
        newLaptop.setCpu(cpuRepository.findById(Long.valueOf(laptopRequest.getCpuId())).get());
        newLaptop.setGpu(gpuRepository.findById(Long.valueOf(laptopRequest.getGpuId())).get());
        newLaptop.setBrand(brandRepository.findById(Long.valueOf(laptopRequest.getBrandId())).get());
        newLaptop.setPrice(Long.valueOf(laptopRequest.getPrice()));
        newLaptop.setRam(Integer.valueOf(laptopRequest.getRam()));
        newLaptop.setHardDrive(Integer.valueOf(laptopRequest.getHardDrive()));
        newLaptop.setWeight(Float.valueOf(laptopRequest.getWeight()));
        newLaptop.setRating(0);
        newLaptop.setWideScreen(Float.valueOf(laptopRequest.getWideScreen()));
        if( !Objects.isNull(laptopRequest) && !Objects.isNull(laptopRequest.getBinaryImage())){
            ImageUtils.downloadImage(laptopRequest.getImage(),laptopRequest.getBinaryImage(), URL_IMG);
        }
        newLaptop.setImage(laptopRequest.getImage());

        //CALL TO PYTHON
        Map<String, String> laptopBody = new HashMap();
        laptopBody.put("price", String.valueOf(newLaptop.getPrice()/price_max));
        laptopBody.put("ram", String.valueOf(newLaptop.getRam()/ram_max));
        laptopBody.put("hard_drive", String.valueOf(newLaptop.getHardDrive()/rom_max));
        laptopBody.put("weight", String.valueOf(newLaptop.getWeight()/weight_max));
        laptopBody.put("brand_rank", String.valueOf(newLaptop.getBrand().getBrandRank()/brand_max));
        laptopBody.put("cpu_rank", String.valueOf(newLaptop.getCpu().getCpuRank()/cpu_max));
        laptopBody.put("gpu_rank", String.valueOf(newLaptop.getGpu().getGpuRank()/gpu_max));
        laptopBody.put("inch", String.valueOf(newLaptop.getWideScreen()/widescreen_max));
        laptopBody.put("type", laptopRequest.getData());

        String    labelForLaptop = null;

        labelForLaptop = apiCallPython.localApiGetDemand().post()
                                       .uri("/laptop")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .body(BodyInserters.fromValue(laptopBody))
                                       .retrieve()
                                       .bodyToMono(String.class)
                                       .block();
        int label;
        try {
            label = Integer.parseInt(labelForLaptop.replace("\"",""));
        }
        catch (NumberFormatException e) {
            System.out.println(e.getCause());
            label = 0;
        }

        newLaptop.setLabel(labelRepository.findById(label).get());
        newLaptop = laptopRepository.save(newLaptop);
        return new ResponseEntity<>("Thêm thành công", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getLaptop (String laptopId) {
        Long lapId;
        try {
            lapId = Long.valueOf(laptopId);
        }
        catch (NumberFormatException e){
            return ResponseEntity.ok("Laptop không tồn tại");
        }
        Optional<Laptop> laptopOptional = laptopRepository.findById(lapId);
        if(!laptopOptional.isEmpty()) return ResponseEntity.ok(laptopOptional.get());
        return ResponseEntity.ok("Laptop không tồn tại");
    }

    public ResponseEntity<?> updateLaptop (LaptopRequest laptopRequest, String laptopId) {
        long lapId;
        try {
            lapId = Long.valueOf(laptopId);
        }
        catch (NumberFormatException e){
            return ResponseEntity.ok("Laptop không tồn tại");
        }
        Optional<Laptop> laptopOptional = laptopRepository.findById(lapId);
        if(laptopOptional.isEmpty()) return ResponseEntity.ok("Laptop không tồn tại");
        Laptop laptop = laptopOptional.get();
        Laptop laptop2 = laptopRepository.findLaptopByNameExcludeLaptopId(laptopRequest.getLaptopName(), lapId);
        if ( laptop2 != null ) {
            return ResponseEntity.ok("Laptop đã tồn tại");
        }
        laptop.setLaptopName(laptopRequest.getLaptopName());
        laptop.setCpu(cpuRepository.findById(Long.valueOf(laptopRequest.getCpuId())).get());
        laptop.setGpu(gpuRepository.findById(Long.valueOf(laptopRequest.getGpuId())).get());
        laptop.setBrand(brandRepository.findById(Long.valueOf(laptopRequest.getBrandId())).get());
        laptop.setPrice(Long.valueOf(laptopRequest.getPrice()));
        laptop.setRam(Integer.valueOf(laptopRequest.getRam()));
        laptop.setHardDrive(Integer.valueOf(laptopRequest.getHardDrive()));
        laptop.setWeight(Float.valueOf(laptopRequest.getWeight()));
        laptop.setRating(0);
        laptop.setWideScreen(Float.valueOf(laptopRequest.getWideScreen()));
        if( !Objects.isNull(laptopRequest) && !Objects.isNull(laptopRequest.getBinaryImage())){
            ImageUtils.downloadImage(laptopRequest.getImage(),laptopRequest.getBinaryImage(), URL_IMG);
        }
        laptop.setImage(laptopRequest.getImage());

        //CALL TO PYTHON
        Map<String, String> laptopBody = new HashMap();
        laptopBody.put("price", String.valueOf(laptop.getPrice()/price_max));
        laptopBody.put("ram", String.valueOf(laptop.getRam()/ram_max));
        laptopBody.put("hard_drive", String.valueOf(laptop.getHardDrive()/rom_max));
        laptopBody.put("weight", String.valueOf(laptop.getWeight()/weight_max));
        laptopBody.put("brand_rank", String.valueOf(laptop.getBrand().getBrandRank()/brand_max));
        laptopBody.put("cpu_rank", String.valueOf(laptop.getCpu().getCpuRank()/cpu_max));
        laptopBody.put("gpu_rank", String.valueOf(laptop.getGpu().getGpuRank()/gpu_max));
        laptopBody.put("inch", String.valueOf(laptop.getWideScreen()/widescreen_max));
        laptopBody.put("type", laptopRequest.getData());

        String    labelForLaptop = null;

        labelForLaptop = apiCallPython.localApiGetDemand().post()
                                      .uri("/laptop")
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .body(BodyInserters.fromValue(laptopBody))
                                      .retrieve()
                                      .bodyToMono(String.class)
                                      .block();
        int label = laptop.getLabel().getLabelId();
        try {
            label = Integer.parseInt(labelForLaptop.replace("\"",""));
        }
        catch (NumberFormatException e) {
            System.out.println(e.getCause());
        }

        laptop.setLabel(labelRepository.findById(label).get());
        laptop = laptopRepository.save(laptop);
        return new ResponseEntity<>("Cập nhật thành công", HttpStatus.OK);
    }
}
