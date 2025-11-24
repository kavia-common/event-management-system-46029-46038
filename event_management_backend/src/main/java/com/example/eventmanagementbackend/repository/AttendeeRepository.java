package com.example.eventmanagementbackend.repository;

import com.example.eventmanagementbackend.model.Attendee;
import com.example.eventmanagementbackend.model.Event;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {

    List<Attendee> findByEvent(Event event);

    long countByEvent(Event event);

    Optional<Attendee> findByEventAndEmailIgnoreCase(Event event, String email);
}
