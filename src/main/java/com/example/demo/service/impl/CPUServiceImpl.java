package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.repository.CPURepository;
import com.example.demo.service.CPUService;

@Service
public class CPUServiceImpl implements CPUService {

    @Autowired
    private CPURepository cpuRepository;
    @Override
    public ResponseEntity<?> findAll () {
        return ResponseEntity.ok(cpuRepository.findAll());
    }
}
