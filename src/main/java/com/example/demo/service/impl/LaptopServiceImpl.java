package com.example.demo.service.impl;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import com.example.demo.bean.ImageUtils;
import com.example.demo.bean.APICallPython;
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
            return ResponseEntity.ok("Laptop name already exited");
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
        laptopBody.put("price", String.valueOf(newLaptop.getPrice()));
        laptopBody.put("ram", String.valueOf(newLaptop.getRam()));
        laptopBody.put("hard_drive", String.valueOf(newLaptop.getHardDrive()));
        laptopBody.put("weight", String.valueOf(newLaptop.getWeight()));
        laptopBody.put("brand_rank", String.valueOf(newLaptop.getBrand().getBrandRank()));
        laptopBody.put("cpu_rank", String.valueOf(newLaptop.getCpu().getCpuRank()));
        laptopBody.put("gpu_rank", String.valueOf(newLaptop.getGpu().getGpuRank()));
        laptopBody.put("inch", String.valueOf(newLaptop.getWideScreen()));

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
            label = 0;
        }

        newLaptop.setLabel(labelRepository.findById(label).get());
        newLaptop = laptopRepository.save(newLaptop);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
