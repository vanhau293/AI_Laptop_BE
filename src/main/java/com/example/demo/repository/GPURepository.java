package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.GPU;

public interface GPURepository extends JpaRepository<GPU, Long> {
}
