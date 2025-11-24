package com.example.eventmanagementbackend.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Event entity representing the persisted event record.
 */
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    private String location;

    private Integer capacity;

    private String organizer;

    /**
     * Comma-separated tags persisted as a single string for simplicity.
     */
    private String tags;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Attendee> attendees = new HashSet<>();

    public Event() {
    }

    public Event(String title, String description, OffsetDateTime startTime, OffsetDateTime endTime, String location, Integer capacity, String organizer, String tags) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.capacity = capacity;
        this.organizer = organizer;
        this.tags = tags;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(OffsetDateTime startTime) { this.startTime = startTime; }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(OffsetDateTime endTime) { this.endTime = endTime; }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) { this.location = location; }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) { this.organizer = organizer; }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) { this.tags = tags; }

    public Set<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<Attendee> attendees) { this.attendees = attendees; }

    public int getAttendanceCount() {
        return attendees != null ? attendees.size() : 0;
    }
}
