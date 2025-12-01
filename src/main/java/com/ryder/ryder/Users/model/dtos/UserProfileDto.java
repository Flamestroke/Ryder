package com.ryder.ryder.Users.model.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect; // <--- Import 1
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.ryder.ryder.Users.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class UserProfileDto {
    private String fullName;
    private String email;
    private String phone;
    private Role role;
}
