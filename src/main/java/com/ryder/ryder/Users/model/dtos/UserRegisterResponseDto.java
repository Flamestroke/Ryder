package com.ryder.ryder.Users.model.dtos;

import com.ryder.ryder.Users.model.enums.Role;
import com.ryder.ryder.Users.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserRegisterResponseDto {
    private long id;
    private String email;
    private String fullName;
    private String phone;
    private Role role;
    private Status accountStatus;
    private Date createdAt;
    private Date updatedAt;
}
