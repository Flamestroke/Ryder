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
public class TripHistoryDto {

    private Long id;
    private LocalDateTime date;
    private String source;
    private String destination;
    private Double fare;
    private TripStatus tripStatus;

}
