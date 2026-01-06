package com.ryder.ryder.Auth.services;

import com.ryder.ryder.Auth.Util.JwtService;
import com.ryder.ryder.Auth.model.dtos.UserLoginRequestDto;
import com.ryder.ryder.Auth.model.dtos.UserLoginResponseDto;
import com.ryder.ryder.Common.Exceptions.InvalidCredentialsException;
import com.ryder.ryder.Users.model.entity.Users;
import com.ryder.ryder.Users.repositories.UsersRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UsersRepo usersRepo;
    private PasswordEncoder passwordEncoder ;
    private JwtService jwtService;

    @Override
    public UserLoginResponseDto loginUser(UserLoginRequestDto request) {

        Users user = usersRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("The email used is invalid!!!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassHash())) {
            throw new InvalidCredentialsException("The password is invalid!!!!");
        }

        var token = jwtService.generateToken(user);

        return new UserLoginResponseDto(token, "Bearer");
    }
}
