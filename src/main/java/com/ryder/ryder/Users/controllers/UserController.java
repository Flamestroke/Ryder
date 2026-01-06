package com.ryder.ryder.Users.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ryder.ryder.Users.model.dtos.UserProfileDto;
import com.ryder.ryder.Users.model.dtos.UserUpdateRequestDto;
import com.ryder.ryder.Users.services.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Show User Profile
    // Maybe remove cuz driver and rider have their own show profile
    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        UserProfileDto profile = userService.getMyProfile(currentEmail);
        return ResponseEntity.ok(profile);
    }

    // Update User Profile
    // Maybe remove cuz driver and rider have their own show profile
    @PutMapping("/me")
    public ResponseEntity<UserProfileDto> updateProfile(@RequestBody UserUpdateRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        UserProfileDto updatedprofile = userService.updateMyProfile(currentEmail, request);

        return ResponseEntity.ok(updatedprofile);
    }
}
