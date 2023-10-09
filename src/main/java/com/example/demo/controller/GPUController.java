package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.GPUService;

@RestController
@RequestMapping("/gpu")
public class GPUController {

    @Autowired
    private GPUService gpuService;
    @GetMapping("")
    public ResponseEntity<?> getCPUs(){
        return gpuService.findAll();
    }
}
