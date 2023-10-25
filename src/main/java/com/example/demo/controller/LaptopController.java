package com.example.demo.controller;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.request.LaptopRequest;
import com.example.demo.service.LaptopService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/laptop")
public class LaptopController {
    @Autowired
    private LaptopService laptopService;

    @GetMapping("")
    public ResponseEntity<?> getListLaptop (@RequestParam(name = "label", defaultValue = "3") String searchLabel,
                                            @RequestParam(name = "demand", defaultValue = "") String searchDemand)
    throws URISyntaxException {
        return laptopService.findLaptop(searchLabel, searchDemand);
    }

    @PostMapping("")
    public ResponseEntity<?> addLaptop (@RequestBody LaptopRequest laptopRequest){
        return laptopService.addLaptop(laptopRequest);
    }

    @GetMapping("/{laptopId}")
    public ResponseEntity<?> getLaptop (@PathVariable String laptopId){
        return laptopService.getLaptop(laptopId);
    }

    @PutMapping("/{laptopId}")
    public ResponseEntity<?> updateLaptop (@RequestBody LaptopRequest laptopRequest, @PathVariable String laptopId){
        return laptopService.updateLaptop(laptopRequest, laptopId);
    }
}
