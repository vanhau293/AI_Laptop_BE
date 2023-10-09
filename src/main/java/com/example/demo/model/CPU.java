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
@Table(name = "CPU")
public class CPU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cpu_id", nullable = false)
    private Long cpuId;

    @Column(name = "cpu_name")
    private String cpuName;

    @Column(name = "cpu_rank")
    private Long cpuRank;

}