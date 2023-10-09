package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.model.Brand;

public interface BrandService {
    ResponseEntity<?> findAll();
}
