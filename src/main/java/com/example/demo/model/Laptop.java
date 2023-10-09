package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Laptop")
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "laptop_id", nullable = false)
    private Long laptopId;

    @Column(name = "laptop_name", nullable = false)
    private String laptopName;

    @ManyToOne
    @JoinColumn(referencedColumnName = "brand_id", name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(referencedColumnName = "cpu_id", name = "cpu_id")
    private CPU cpu;

    @ManyToOne
    @JoinColumn(referencedColumnName = "gpu_id", name = "gpu_id")
    private GPU gpu;

    @ManyToOne
    @JoinColumn(referencedColumnName = "label_id", name = "label_id")
    private DemandLabel label;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "ram", nullable = false)
    private int ram;

    @Column(name = "hard_drive", nullable = false)
    private int hardDrive;

    @Column(name = "inch", nullable = false)
    private double wideScreen;

    @Column(name = "weight", nullable = false)
    private double weight;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "laptop", cascade = CascadeType.ALL)
    private List<Review> reviewList;


}