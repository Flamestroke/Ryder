package com.ryder.ryder.Users.services;

import com.ryder.ryder.Common.Exceptions.EmailAlreadyExistsException;
import com.ryder.ryder.Common.Exceptions.PhoneAlreadyExistsException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ryder.ryder.Users.model.dtos.UserProfileDto;
import com.ryder.ryder.Users.model.dtos.UserRegisterRequestDto;
import com.ryder.ryder.Users.model.dtos.UserRegisterResponseDto;
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

    @Override
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto request) {

        if (usersRepo.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already Registered!!");
        }

        if (usersRepo.existsByPhone(request.getPhone())) {
            throw new PhoneAlreadyExistsException("Phone is already Registered!!");
        }

        // 1. Encode password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 2. Map DTO → Entity
        Users user = userMapper.toEntity(request, encodedPassword);

        // 3. Save
        Users saved = usersRepo.save(user);

        return userMapper.toRegisterResponse(saved);

    }

    @Override
    public UserProfileDto getMyProfile(String email) {
        Users user = usersRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!!"));

        return userMapper.toProfileDto(user);
    }

}
