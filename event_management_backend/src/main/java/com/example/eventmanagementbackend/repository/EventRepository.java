package com.example.eventmanagementbackend.repository;

import com.example.eventmanagementbackend.model.Event;
import java.time.OffsetDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByOrganizerContainingIgnoreCase(String organizer, Pageable pageable);

    Page<Event> findByTagsContainingIgnoreCase(String tag, Pageable pageable);

    Page<Event> findByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    Page<Event> findByOrganizerContainingIgnoreCaseAndTagsContainingIgnoreCase(String organizer, String tag, Pageable pageable);

    Page<Event> findByOrganizerContainingIgnoreCaseAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(
            String organizer, OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    Page<Event> findByTagsContainingIgnoreCaseAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(
            String tag, OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    Page<Event> findByOrganizerContainingIgnoreCaseAndTagsContainingIgnoreCaseAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(
            String organizer, String tag, OffsetDateTime from, OffsetDateTime to, Pageable pageable);
}
