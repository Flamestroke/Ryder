package com.ryder.ryder.Users.controllers;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ryder.ryder.Users.model.dtos.UserProfileDto;
import com.ryder.ryder.Users.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // @GetMapping("/me")
    // public ResponseEntity<UserProfileDto> getMyProfile() {
    // // 1. Who is logged in?
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();
    // String currentEmail = authentication.getName();

    // // 2. Get their profile (Service uses MapStruct internally)
    // UserProfileDto profile = userService.getMyProfile(currentEmail);

    // // 3. Return it
    // return ResponseEntity.ok(profile);
    // }

    // @GetMapping("/me")
    // public String getMyProfile() {
    // System.out.println("--- BYPASS TEST HIT ---");
    // return "IF YOU SEE THIS, THE APP IS FINE";
    // }

    // @GetMapping("/me")
    // public ResponseEntity<UserProfileDto> getMyProfile() {
    // // Hardcode your email for now (since we are bypassed)
    // String testEmail = "jannet@example.com";

    // UserProfileDto profile = userService.getMyProfile(testEmail);
    // return ResponseEntity.ok(profile);
    // }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        UserProfileDto profile = userService.getMyProfile(currentEmail);
        return ResponseEntity.ok(profile);
    }
}
