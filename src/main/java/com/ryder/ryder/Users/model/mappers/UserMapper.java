package com.ryder.ryder.Users.model.mappers;

import org.mapstruct.*;

import com.ryder.ryder.Users.model.dtos.UserRegisterRequestDto;
import com.ryder.ryder.Users.model.dtos.UserRegisterResponseDto;
import com.ryder.ryder.Users.model.entity.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // @Mapping(target = "id", ignore = true)
    // @Mapping(target = "passHash", expression = "java(hashPassword(request.getPassword()))")
    // @Mapping(target = "accountStatus", constant = "ACTIVE")
    // @Mapping(target = "createdAt", expression = "java(new Date())")
    // @Mapping(target = "updatedAt", expression = "java(new Date())")
    // Users toEntity(UserRegisterRequestDto request);

    // UserRegisterResponseDto toResponse(Users user);

    // // MapStruct can't call non-static local methods, so:
    // static String hashPassword(String password) {
    //     // call your real password encoder here
    //     return password; // placeholder
    // }

     // request + encodedPassword → Users entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passHash", source = "encodedPassword")
    @Mapping(target = "accountStatus", expression = "java(Status.ACTIVE)")
    @Mapping(target = "createdAt", expression = "java(new Date())")
    @Mapping(target = "updatedAt", expression = "java(new Date())")
    @Mapping(
        target = "role",
        expression = "java( request.getRole() != null ? request.getRole() : Role.RIDER )"
    )
    Users toEntity(UserRegisterRequestDto request, String encodedPassword);

    // entity → response DTO
    UserRegisterResponseDto toRegisterResponse(Users user);
}
