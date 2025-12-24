package com.ryder.ryder.Trips.model.dtos;

import java.time.LocalDateTime;

import com.ryder.ryder.Location.model.entity.Coordinates;
import com.ryder.ryder.Trips.model.enums.TripStatus;
import com.ryder.ryder.Users.model.dtos.UserProfileDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripResponseDto {

    private Long id;
    private UserProfileDto driver;
    private Coordinates source;
    private Coordinates destination;
    private TripStatus tripStatus;
    private Double fare;
    private String otp;
    private LocalDateTime requestedAt;
}
