package com.ryder.ryder.Users.model.mappers;

import org.mapstruct.*;

import com.ryder.ryder.Users.model.dtos.UserProfileDto;
import com.ryder.ryder.Users.model.dtos.UserRegisterRequestDto;
import com.ryder.ryder.Users.model.dtos.UserRegisterResponseDto;
import com.ryder.ryder.Users.model.dtos.UserUpdateRequestDto;
import com.ryder.ryder.Users.model.entity.Users;

import com.ryder.ryder.Users.model.enums.Role;
import com.ryder.ryder.Users.model.enums.Status;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "passHash", source = "encodedPassword")
    @Mapping(target = "accountStatus", expression = "java(Status.ACTIVE)")
    @Mapping(target = "createdAt", expression = "java(new Date())")
    @Mapping(target = "updatedAt", expression = "java(new Date())")
    @Mapping(target = "role", expression = "java( request.getRole() != null ? request.getRole() : Role.RIDER )")
    Users toEntity(UserRegisterRequestDto request, String encodedPassword);

    // entity → response DTO
    UserRegisterResponseDto toRegisterResponse(Users user);

    UserProfileDto toProfileDto(Users user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateRequestDto dto, @MappingTarget Users entity);
}
