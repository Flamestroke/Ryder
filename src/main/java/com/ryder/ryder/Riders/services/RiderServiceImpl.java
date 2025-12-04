package com.ryder.ryder.Riders.services;

import org.springframework.stereotype.Service;

import com.ryder.ryder.Users.model.dtos.UserProfileDto;
import com.ryder.ryder.Users.model.dtos.UserUpdateRequestDto;
import com.ryder.ryder.Users.services.UserService;
import com.ryder.ryder.Users.services.UserServiceImpl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final UserService userService;

    @Override
    public UserProfileDto getMyProfileDto(String email) {
        return userService.getMyProfile(email);
    }

    @Override
    public UserProfileDto updateMyProfile(String email, UserUpdateRequestDto request) {
        return userService.updateMyProfile(email, request);
    }

}
