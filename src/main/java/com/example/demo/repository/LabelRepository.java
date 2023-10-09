package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Brand;
import com.example.demo.model.DemandLabel;

public interface LabelRepository extends JpaRepository<DemandLabel, Long> {
}
