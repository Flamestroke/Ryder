package com.ryder.ryder.Users.services;

import com.ryder.ryder.Users.model.dtos.UserProfileDto;
import com.ryder.ryder.Users.model.dtos.UserRegisterRequestDto;
import com.ryder.ryder.Users.model.dtos.UserRegisterResponseDto;
import com.ryder.ryder.Users.model.dtos.UserUpdateRequestDto;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserRegisterResponseDto registerUser(UserRegisterRequestDto request);

    UserProfileDto getMyProfile(String email);

    UserProfileDto updateMyProfile(String email, UserUpdateRequestDto request);

}
