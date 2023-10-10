package com.example.demo.service;

import java.net.URISyntaxException;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.request.LaptopRequest;

public interface LaptopService {
    ResponseEntity<?> findLaptop(String labelId, String demand) throws URISyntaxException;
    ResponseEntity<?> addLaptop(LaptopRequest laptopRequest);
}
