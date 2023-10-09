package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
