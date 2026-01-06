package com.ryder.ryder.Users.services;

import com.ryder.ryder.Common.Exceptions.EmailAlreadyExistsException;
import com.ryder.ryder.Common.Exceptions.PhoneAlreadyExistsException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ryder.ryder.Users.model.dtos.UserProfileDto;
import com.ryder.ryder.Users.model.dtos.UserRegisterRequestDto;
import com.ryder.ryder.Users.model.dtos.UserRegisterResponseDto;
import com.ryder.ryder.Users.model.dtos.UserUpdateRequestDto;
import com.ryder.ryder.Users.model.entity.Users;
import com.ryder.ryder.Users.model.mappers.UserMapper;
import com.ryder.ryder.Users.repositories.UsersRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepo usersRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // Register new User
    @Override
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto request) {

        if (usersRepo.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already Registered!!");
        }

        if (usersRepo.existsByPhone(request.getPhone())) {
            throw new PhoneAlreadyExistsException("Phone is already Registered!!");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Users user = userMapper.toEntity(request, encodedPassword);

        Users savedUser = usersRepo.save(user);

        return userMapper.toRegisterResponse(savedUser);

    }

    //Show User Profile
    @Override
    public UserProfileDto getMyProfile(String email) {

        Users user = usersRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!!"));

        return userMapper.toProfileDto(user);
    }

    // Update User Profile
    @Override
    public UserProfileDto updateMyProfile(String email, UserUpdateRequestDto request) {
        
        Users user = usersRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!!"));

        userMapper.updateUserFromDto(request, user);

        Users updatedUser = usersRepo.save(user);

        return userMapper.toProfileDto(updatedUser);
    }

}
