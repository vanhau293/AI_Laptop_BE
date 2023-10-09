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
@Table(name = "Demand_label")
public class DemandLabel {
    @Id
    @Column(name = "label_id", nullable = false)
    private Long labelId;

    @Column(name = "label_name")
    private String labelName;

}