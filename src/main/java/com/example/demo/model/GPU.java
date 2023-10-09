package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "GPU")
public class GPU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gpu_id", nullable = false)
    private Long gpuId;

    @Column(name = "gpu_name")
    private String gpuName;

    @Column(name = "gpu_rank")
    private Long gpuRank;

}