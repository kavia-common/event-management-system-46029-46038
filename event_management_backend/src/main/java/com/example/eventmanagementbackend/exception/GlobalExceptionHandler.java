package com.example.eventmanagementbackend.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Schema(description = "Generic API error response")
    public record ApiError(
            String error,
            String message,
            int status,
            String path,
            OffsetDateTime timestamp,
            Map<String, String> validationErrors
    ) {}

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, org.springframework.web.context.request.WebRequest req) {
        return buildError(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), req, null);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, org.springframework.web.context.request.WebRequest req) {
        return buildError(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), req, null);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException ex, org.springframework.web.context.request.WebRequest req) {
        return buildError(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), req, null);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ApiError> handleValidation(Exception ex, org.springframework.web.context.request.WebRequest req) {
        Map<String, String> fieldErrors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException manv) {
            manv.getBindingResult().getFieldErrors().forEach(f -> fieldErrors.put(f.getField(), f.getDefaultMessage()));
        } else if (ex instanceof BindException be) {
            be.getBindingResult().getFieldErrors().forEach(f -> fieldErrors.put(f.getField(), f.getDefaultMessage()));
        }
        return buildError(HttpStatus.BAD_REQUEST, "Validation Failed", "One or more fields have an error", req, fieldErrors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, org.springframework.web.context.request.WebRequest req) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getConstraintViolations().forEach(v -> fieldErrors.put(v.getPropertyPath().toString(), v.getMessage()));
        return buildError(HttpStatus.BAD_REQUEST, "Validation Failed", "One or more fields have an error", req, fieldErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, org.springframework.web.context.request.WebRequest req) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), req, null);
    }

    private ResponseEntity<ApiError> buildError(HttpStatus status, String error, String message, org.springframework.web.context.request.WebRequest req, Map<String, String> validationErrors) {
        String path = req.getDescription(false).replace("uri=", "");
        ApiError body = new ApiError(error, message, status.value(), path, OffsetDateTime.now(), validationErrors);
        return ResponseEntity.status(status).body(body);
    }
}
