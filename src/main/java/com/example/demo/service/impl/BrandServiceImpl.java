package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.repository.BrandRepository;
import com.example.demo.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;
    @Override
    public ResponseEntity<?> findAll () {
        return ResponseEntity.ok(brandRepository.findAll());
    }
}
