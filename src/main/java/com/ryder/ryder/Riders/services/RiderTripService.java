package com.ryder.ryder.Riders.services;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

import com.ryder.ryder.Trips.model.dtos.TripHistoryDto;
import com.ryder.ryder.Trips.model.dtos.TripRequestDto;
import com.ryder.ryder.Trips.model.dtos.TripResponseDto;

public interface RiderTripService {

    TripResponseDto getCurentTrip(String email);

    Page<TripHistoryDto> getTripHistory(String email, int page, int size);

    TripResponseDto requestRide(String email, TripRequestDto request);

}
