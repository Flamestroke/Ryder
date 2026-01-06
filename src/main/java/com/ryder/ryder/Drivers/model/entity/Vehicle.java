package com.ryder.ryder.Drivers.model.entity;

import com.ryder.ryder.Users.model.entity.Users;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String brand;
    private String model;
    private String color;
    private String licensePlate;
    private Integer capacity;

    @OneToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Users driver;

}
