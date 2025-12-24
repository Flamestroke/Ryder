package com.ryder.ryder.Drivers.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ryder.ryder.Drivers.model.dtos.DriverLocationDto;
import com.ryder.ryder.Drivers.model.dtos.StartRideDto;
import com.ryder.ryder.Trips.model.dtos.TripResponseDto;

@Service
public interface DriverService {

    DriverLocationDto updateCurrentLocation(String driverEmail, DriverLocationDto driverLocationDto);

    List<TripResponseDto> getNearbyRides(String driverEmail);

    TripResponseDto acceptRide(Long tripId, String driverEmail);

    TripResponseDto startRide(String driverEmail, StartRideDto startRideDto);

    TripResponseDto completeRide(Long tripId, String driverEmail);

}
