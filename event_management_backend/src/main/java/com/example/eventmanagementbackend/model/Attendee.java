package com.example.eventmanagementbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "attendees", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"event_id", "email"})
})
public class Attendee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    public Attendee() {
    }

    public Attendee(String name, String email, Event event) {
        this.name = name;
        this.email = email;
        this.event = event;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Event getEvent() { return event; }

    public void setEvent(Event event) { this.event = event; }
}
