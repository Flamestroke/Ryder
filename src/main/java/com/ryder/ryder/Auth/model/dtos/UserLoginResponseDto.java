package com.ryder.ryder.Auth.model.dtos;

import com.ryder.ryder.Users.model.enums.Role;
import com.ryder.ryder.Users.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponseDto {
    //    private String accessToken;
//    private String tokenType = "Bearer";
    private long id;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private Status accountStatus;
    private boolean loginSuccessful;
}
