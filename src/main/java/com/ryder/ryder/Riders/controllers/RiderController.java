package com.ryder.ryder.Riders.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryder.ryder.Riders.services.RiderService;
import com.ryder.ryder.Riders.services.RiderTripService;
import com.ryder.ryder.Trips.model.dtos.TripHistoryDto;
import com.ryder.ryder.Trips.model.dtos.TripRequestDto;
import com.ryder.ryder.Trips.model.dtos.TripResponseDto;
import com.ryder.ryder.Trips.services.TripService;
import com.ryder.ryder.Users.model.dtos.UserProfileDto;
import com.ryder.ryder.Users.model.dtos.UserUpdateRequestDto;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
@PreAuthorize("hasRole('RIDER')")
public class RiderController {

    private final RiderService riderService;
    private final RiderTripService riderTripService;

    // Show Rider(customer) Profile
    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(riderService.getMyProfileDto(authentication.getName()));
    }

    // Update Rider(customer) Profile
    @PutMapping("/me")
    public ResponseEntity<UserProfileDto> updateMyProfile(@RequestBody UserUpdateRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(riderService.updateMyProfile(authentication.getName(), request));
    }

    // Trip Rider(customer) is currently in
    @GetMapping("/trips/current")
    public ResponseEntity<TripResponseDto> getCurrentTrip() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(riderTripService.getCurentTrip(authentication.getName()));
    }

    // Previous Trips of Rider(customer)
    @GetMapping("/trips/past")
    public ResponseEntity<Page<TripHistoryDto>> getTripHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(riderTripService.getTripHistory(authentication.getName(), page, size));
    }

    // Requesting(Booking) of Trip
    @PostMapping("/trips/request")
    public ResponseEntity<TripResponseDto> requestRide(@RequestBody TripRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(riderTripService.requestRide(authentication.getName(), request));
    }

}
