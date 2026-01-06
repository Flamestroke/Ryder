package com.ryder.ryder.Drivers.model.dtos;

import lombok.Data;

@Data
public class VehicleDataDto {
    private String brand;
    private String model;
    private String color;
    private String licensePlate;
    private Integer capacity;
}
