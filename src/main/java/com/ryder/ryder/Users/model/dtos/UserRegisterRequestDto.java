package com.ryder.ryder.Users.model.dtos;

import com.ryder.ryder.Users.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequestDto {

    @NotBlank(message = "Email cannot be blank!")
    @Email(message = "Invalid email format!")
    private String email;

    @NotBlank(message = "Name cannot be blank!")
    private String fullName;

    @NotBlank(message = "Phone cannot be blank!")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 8, message = "Password must be at least 8 characters!")
    private String password;

    private Role role;
}
