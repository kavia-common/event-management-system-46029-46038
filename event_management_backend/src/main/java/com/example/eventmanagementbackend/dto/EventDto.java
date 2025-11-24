package com.example.eventmanagementbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.List;

// PUBLIC_INTERFACE
/**
 * Event DTO for create/update requests.
 */
@Schema(description = "Event create/update request")
public class EventDto {

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Event title", example = "Tech Conference 2025")
    private String title;

    @Size(max = 2000)
    @Schema(description = "Event description", example = "A conference about modern technologies.")
    private String description;

    @NotNull
    @Schema(description = "Start time in ISO-8601 format", example = "2025-12-01T09:00:00Z")
    private OffsetDateTime startTime;

    @NotNull
    @Schema(description = "End time in ISO-8601 format", example = "2025-12-01T17:00:00Z")
    private OffsetDateTime endTime;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Location", example = "San Francisco, CA")
    private String location;

    @NotNull
    @Min(1)
    @Schema(description = "Capacity (maximum number of attendees)", example = "200")
    private Integer capacity;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Organizer name", example = "ACME Events")
    private String organizer;

    @Schema(description = "Tags list", example = "[\"tech\",\"conference\"]")
    private List<@Size(max = 50) String> tags;

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public OffsetDateTime getStartTime() { return startTime; }

    public void setStartTime(OffsetDateTime startTime) { this.startTime = startTime; }

    public OffsetDateTime getEndTime() { return endTime; }

    public void setEndTime(OffsetDateTime endTime) { this.endTime = endTime; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public Integer getCapacity() { return capacity; }

    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getOrganizer() { return organizer; }

    public void setOrganizer(String organizer) { this.organizer = organizer; }

    public List<String> getTags() { return tags; }

    public void setTags(List<String> tags) { this.tags = tags; }
}
