package com.ryder.ryder.Auth.controllers;

import com.ryder.ryder.Auth.model.dtos.UserLoginRequestDto;
import com.ryder.ryder.Auth.model.dtos.UserLoginResponseDto;
import com.ryder.ryder.Auth.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(@Valid @RequestBody UserLoginRequestDto request) {
        UserLoginResponseDto response = authService.loginUser(request);
        return ResponseEntity.ok(response);
    }

}
