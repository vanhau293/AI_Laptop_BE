package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CPUService;

@RestController
@RequestMapping("/cpu")
public class CPUController {

    @Autowired
    private CPUService cpuService;
    @GetMapping("")
    public ResponseEntity<?> getCPUs(){
        return cpuService.findAll();
    }
}
