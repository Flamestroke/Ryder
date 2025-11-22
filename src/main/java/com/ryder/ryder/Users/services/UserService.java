package com.ryder.ryder.Users.services;

import com.ryder.ryder.Users.model.dtos.UserRegisterRequestDto;
import com.ryder.ryder.Users.model.dtos.UserRegisterResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserRegisterResponseDto registerUser(UserRegisterRequestDto request);

}
