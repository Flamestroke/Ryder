package com.ryder.ryder.Common.Exceptions;

import com.ryder.ryder.Common.Dtos.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        // General Business Logic Errors (like "Active Trip")
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException ex,
                        HttpServletRequest request) {

                return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);

        }

        // Validation errors from DTOs
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponseDto> handleValidationException(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {
                Map<String, String> validationErrors = new HashMap<>();
                for (var error : ex.getBindingResult().getAllErrors()) {
                        String fieldName = ((FieldError) error).getField();
                        String errorMessage = error.getDefaultMessage();
                        validationErrors.put(fieldName, errorMessage);
                }

                String message = "Validation failed";
                // .validationErrors(validationErrors)

                return buildResponse(HttpStatus.BAD_REQUEST, message, request);

        }

        // Incorrect JSON / wrong types in request body
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErrorResponseDto> handleNotReadable(
                        HttpMessageNotReadableException ex,
                        HttpServletRequest request) {

                String message = "Malformed JSON request";

                return buildResponse(HttpStatus.BAD_REQUEST, message, request);

        }

        // Duplicate / constraint errors from DB (fallback)
        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErrorResponseDto> handleDataIntegrity(
                        DataIntegrityViolationException ex,
                        HttpServletRequest request) {

                String message = "Data integrity violation (possibly duplicate or invalid data)";

                return buildResponse(HttpStatus.CONFLICT, message, request);

        }

        // Invalid Credentials Error
        @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<ErrorResponseDto> handleInvalidCredentials(
                        InvalidCredentialsException ex,
                        HttpServletRequest request) {

                return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);

        }

        // Email already exists Error
        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<ErrorResponseDto> handleUserAlreadyExists(
                        EmailAlreadyExistsException ex,
                        HttpServletRequest request) {

                return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);

        }

        // Phone already exists Error
        @ExceptionHandler(PhoneAlreadyExistsException.class)
        public ResponseEntity<ErrorResponseDto> handlePhoneAlreadyExists(
                        PhoneAlreadyExistsException ex,
                        HttpServletRequest request) {

                return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);

        }

        // Not Found Error
        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<ErrorResponseDto> handleNotFound(
                        NotFoundException ex,
                        HttpServletRequest request) {

                return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);

        }

        // For anything unexpected
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponseDto> handleGeneric(
                        Exception ex,
                        HttpServletRequest request) {

                return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);

        }

        private ResponseEntity<ErrorResponseDto> buildResponse(HttpStatus status, String message,
                        HttpServletRequest request) {
                ErrorResponseDto body = ErrorResponseDto.builder()
                                .timestamp(LocalDateTime.now())
                                .status(status.value())
                                .error(status.getReasonPhrase())
                                .message(message)
                                .path(request.getRequestURI())
                                .build();
                return new ResponseEntity<>(body, status);
        }
}