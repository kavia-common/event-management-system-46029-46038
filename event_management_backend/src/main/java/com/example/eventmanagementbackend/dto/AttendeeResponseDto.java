package com.example.eventmanagementbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

// PUBLIC_INTERFACE
/**
 * Attendee read model.
 */
@Schema(description = "Attendee response model")
public class AttendeeResponseDto {

    private Long id;
    private String name;
    private String email;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
