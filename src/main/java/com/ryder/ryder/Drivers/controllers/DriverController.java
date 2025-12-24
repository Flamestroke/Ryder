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
import com.ryder.ryder.Drivers.services.DriverService;
import com.ryder.ryder.Trips.model.dtos.TripResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
@PreAuthorize("hasRole('DRIVER')")
public class DriverController {
    private final DriverService driverService;

    @PatchMapping("/location")
    public ResponseEntity<DriverLocationDto> updateCurrentLocation(@RequestBody DriverLocationDto driverLocationDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.updateCurrentLocation(authentication.getName(), driverLocationDto));
    }

    @GetMapping("/rides/nearby")
    public ResponseEntity<List<TripResponseDto>> getNearbyRides() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.getNearbyRides(authentication.getName()));
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<TripResponseDto> acceptRides(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.acceptRide(id, authentication.getName()));
    }

    @PostMapping("/ride/start")
    public ResponseEntity<TripResponseDto> startRide(@RequestBody StartRideDto startRideDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.startRide(authentication.getName(), startRideDto));
    }

    @PostMapping("/ride/completed/{id}")
    public ResponseEntity<TripResponseDto> completeRide(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(driverService.completeRide(id, authentication.getName()));
    }

}
