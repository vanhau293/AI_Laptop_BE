package com.example.demo.model.request;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LaptopRequest {

    @JsonProperty("laptopName")
    @NotEmpty(message = "laptopName is required")
    private String laptopName;

    @JsonProperty("brandId")
    @NotEmpty(message = "brandId is required")
    private String brandId;

    @JsonProperty("cpuId")
    @NotEmpty(message = "cpuId is required")
    private String cpuId;

    @NotEmpty(message = "gpuId is required")
    private String gpuId;

    @NotEmpty(message = "price is required")
    private String price;

    @NotEmpty(message = "ram is required")
    private String ram;

    @Column(name = "image")
    private String image;

    @NotEmpty(message = "hardDrive is required")
    private String hardDrive;

    @NotEmpty(message = "wideScreen is required")
    private String wideScreen;

    @NotEmpty(message = "wideScreen is required")
    private String weight;
}

