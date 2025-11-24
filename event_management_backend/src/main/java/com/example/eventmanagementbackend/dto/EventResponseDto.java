package com.example.eventmanagementbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.List;

// PUBLIC_INTERFACE
/**
 * Event read model for API responses.
 */
@Schema(description = "Event response model")
public class EventResponseDto {

    @Schema(description = "Event ID")
    private Long id;

    @Schema(description = "Title")
    private String title;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Start time")
    private OffsetDateTime startTime;

    @Schema(description = "End time")
    private OffsetDateTime endTime;

    @Schema(description = "Location")
    private String location;

    @Schema(description = "Capacity")
    private Integer capacity;

    @Schema(description = "Organizer")
    private String organizer;

    @Schema(description = "Tags")
    private List<String> tags;

    @Schema(description = "Current number of attendees")
    private Integer attendanceCount;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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

    public Integer getAttendanceCount() { return attendanceCount; }

    public void setAttendanceCount(Integer attendanceCount) { this.attendanceCount = attendanceCount; }
}
