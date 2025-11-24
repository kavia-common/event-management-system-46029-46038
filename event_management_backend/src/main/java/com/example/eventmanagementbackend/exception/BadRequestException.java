package com.example.eventmanagementbackend.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}
