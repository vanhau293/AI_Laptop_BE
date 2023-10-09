package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.demo.repository.LaptopRepository;
import com.example.demo.service.LaptopService;

public class LaptopServiceImpl implements LaptopService {
    @Autowired
    private LaptopRepository laptopRepository;
    @Override
    public ResponseEntity<?> findAll () {
        return ResponseEntity.ok(laptopRepository.findAll());
    }
}
