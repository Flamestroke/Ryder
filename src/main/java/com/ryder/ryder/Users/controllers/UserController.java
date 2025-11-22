package com.ryder.ryder.Users.controllers;

import com.ryder.ryder.Users.model.dtos.UserRegisterRequestDto;
import com.ryder.ryder.Users.model.dtos.UserRegisterResponseDto;
import com.ryder.ryder.Users.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> registerUser(@Valid @RequestBody UserRegisterRequestDto request) {
        UserRegisterResponseDto response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @GetMapping("/auth/login")
//    public ResponseEntity<xxx> zzz(@Valid @RequestBody www request) {
//        xxx response = yyy.zzz(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
}
