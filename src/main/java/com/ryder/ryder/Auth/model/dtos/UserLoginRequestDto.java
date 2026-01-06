package com.ryder.ryder.Auth.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDto {

    @NotBlank(message = "Email cannot be blank!")
    @Email(message = "Invalid email format!")
    private String email;

    @NotBlank(message = "Password cannot be blank!")
    private String password;

}
