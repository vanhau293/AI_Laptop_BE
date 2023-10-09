package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.repository.GPURepository;
import com.example.demo.service.GPUService;

@Service
public class GPUServiceImpl implements GPUService {

    @Autowired
    private GPURepository gpuRepository;
    @Override
    public ResponseEntity<?> findAll () {
        return ResponseEntity.ok(gpuRepository.findAll());
    }
}
