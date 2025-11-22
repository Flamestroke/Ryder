package com.ryder.ryder.Common.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ryder.ryder.Users.model.enums.Role;
import com.ryder.ryder.Users.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Data
@Builder
public class ErrorResponseDto {

    @JsonFormat(pattern = "yyyy-mm-dd:HH:mm:ss")
    private LocalDateTime timestamp;

    private int status;
    private String error;
    private String message;
    private String path;

    private Map<String, String> validationErrors;
}
