package com.ryder.ryder.Riders.services;

import org.springframework.stereotype.Service;

import com.ryder.ryder.Users.model.dtos.UserProfileDto;
import com.ryder.ryder.Users.model.dtos.UserUpdateRequestDto;

@Service
public interface RiderService {

    UserProfileDto getMyProfileDto(String email);

    UserProfileDto updateMyProfile(String email, UserUpdateRequestDto request);

}
