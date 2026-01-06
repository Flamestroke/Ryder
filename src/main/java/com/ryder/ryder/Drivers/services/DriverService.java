package com.ryder.ryder.Drivers.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ryder.ryder.Drivers.model.dtos.DriverLocationDto;
import com.ryder.ryder.Drivers.model.dtos.StartRideDto;
import com.ryder.ryder.Drivers.model.dtos.VehicleDataDto;
import com.ryder.ryder.Trips.model.dtos.TripResponseDto;
import com.ryder.ryder.Users.model.dtos.UserProfileDto;
import com.ryder.ryder.Users.model.dtos.UserUpdateRequestDto;

@Service
public interface DriverService {

    DriverLocationDto updateCurrentLocation(String driverEmail, DriverLocationDto driverLocationDto);

    List<TripResponseDto> getNearbyRides(String driverEmail);

    TripResponseDto acceptRide(Long tripId, String driverEmail);

    TripResponseDto startRide(String driverEmail, StartRideDto startRideDto);

    TripResponseDto completeRide(Long tripId, String driverEmail);

    VehicleDataDto addVehicle(String driverEmail, VehicleDataDto vehicleDataDto);

    UserProfileDto getMyProfileDto(String driverEmail);

    UserProfileDto updateMyProfile(String driverEmail, UserUpdateRequestDto request);

}
