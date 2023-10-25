package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Laptop;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {
    @Query(value = "select * from laptop order by rating_avg desc", nativeQuery = true)
    List<Laptop> findLaptop();

    @Query(value = "select * from laptop where label_id = :labelId order by rating_avg desc", nativeQuery = true)
    List<Laptop> findLaptopByLabel(int labelId);

    @Query(value = "select * from laptop where brand_id = :brandId order by rating_avg desc", nativeQuery = true)
    List<Laptop> findLaptopByBrand(int brandId);

    @Query(value = "select * from laptop where laptop_name = :name", nativeQuery = true)
    Laptop findLaptopByName(String name);

    @Query(value = "select * from laptop where laptop_name = :name and laptop_id != :laptopId", nativeQuery = true)
    Laptop findLaptopByNameExcludeLaptopId(String name, long laptopId);
}
