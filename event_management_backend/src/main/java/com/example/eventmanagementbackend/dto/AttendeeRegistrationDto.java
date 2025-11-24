package com.example.eventmanagementbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// PUBLIC_INTERFACE
/**
 * Attendee registration request.
 */
@Schema(description = "Attendee registration request")
public class AttendeeRegistrationDto {

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Attendee name", example = "Jane Doe")
    private String name;

    @NotBlank
    @Email
    @Size(max = 255)
    @Schema(description = "Attendee email", example = "jane@example.com")
    private String email;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
