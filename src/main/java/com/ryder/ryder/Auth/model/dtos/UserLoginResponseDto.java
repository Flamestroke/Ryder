package com.ryder.ryder.Auth.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";
}
