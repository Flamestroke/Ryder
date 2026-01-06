package com.ryder.ryder.Drivers.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ryder.ryder.Drivers.model.dtos.DriverLocationDto;
import com.ryder.ryder.Drivers.model.dtos.StartRideDto;
import com.ryder.ryder.Drivers.model.dtos.VehicleDataDto;
import com.ryder.ryder.Drivers.services.DriverService;
import com.ryder.ryder.Trips.model.dtos.TripResponseDto;
import com.ryder.ryder.Users.model.dtos.UserProfileDto;
import com.ryder.ryder.Users.model.dtos.UserUpdateRequestDto;
import com.ryder.ryder.Users.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
@PreAuthorize("hasRole('DRIVER')")
public class DriverController {
    private final DriverService driverService;

    // Show Driver Profile
    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.getMyProfileDto(authentication.getName()));
    }

    // Update Driver Profile
    @PutMapping("/me")
    public ResponseEntity<UserProfileDto> updateMyProfile(@RequestBody UserUpdateRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.updateMyProfile(authentication.getName(), request));
    }

    // Add Vehicle Details
    @PostMapping("/vehicle")
    public ResponseEntity<VehicleDataDto> addVehicle(@RequestBody VehicleDataDto vehicleDataDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.addVehicle(authentication.getName(), vehicleDataDto));
    }

    // Update location endpoint
    @PatchMapping("/location")
    public ResponseEntity<DriverLocationDto> updateCurrentLocation(@RequestBody DriverLocationDto driverLocationDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.updateCurrentLocation(authentication.getName(), driverLocationDto));
    }

    // Rides(Customers) nearby current location
    @GetMapping("/rides/nearby")
    public ResponseEntity<List<TripResponseDto>> getNearbyRides() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.getNearbyRides(authentication.getName()));
    }

    // Ride acceptance
    @PostMapping("/accept/{id}")
    public ResponseEntity<TripResponseDto> acceptRides(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.acceptRide(id, authentication.getName()));
    }

    // Start ACCEPTED Ride
    @PostMapping("/ride/start")
    public ResponseEntity<TripResponseDto> startRide(@RequestBody StartRideDto startRideDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.startRide(authentication.getName(), startRideDto));
    }

    // Complete STARTED Ride
    @PostMapping("/ride/completed/{id}")
    public ResponseEntity<TripResponseDto> completeRide(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.completeRide(id, authentication.getName()));
    }

}
