package com.ryder.ryder.Auth.services;

import com.ryder.ryder.Auth.model.dtos.UserLoginRequestDto;
import com.ryder.ryder.Auth.model.dtos.UserLoginResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    UserLoginResponseDto loginUser(UserLoginRequestDto request);
}
