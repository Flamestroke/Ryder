package com.ryder.ryder.Trips.model.dtos;

import com.ryder.ryder.Location.model.entity.Coordinates;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRequestDto {
    private Coordinates source;
    private Coordinates destination;
    //vehicle type
}
