package com.example.eventmanagementbackend.service;

import com.example.eventmanagementbackend.dto.EventDto;
import com.example.eventmanagementbackend.exception.BadRequestException;
import com.example.eventmanagementbackend.exception.NotFoundException;
import com.example.eventmanagementbackend.mapper.EventMapper;
import com.example.eventmanagementbackend.model.Event;
import com.example.eventmanagementbackend.repository.AttendeeRepository;
import com.example.eventmanagementbackend.repository.EventRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository, AttendeeRepository attendeeRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.attendeeRepository = attendeeRepository;
        this.eventMapper = eventMapper;
    }

    @Transactional
    public Event create(EventDto dto) {
        validateDates(dto.getStartTime(), dto.getEndTime());
        Event entity = eventMapper.toEntity(dto);
        Event saved = eventRepository.save(entity);
        log.info("Created event id={}", saved.getId());
        return saved;
    }

    @Transactional(readOnly = true)
    public Page<Event> list(Optional<OffsetDateTime> from,
                            Optional<OffsetDateTime> to,
                            Optional<String> organizer,
                            Optional<String> tag,
                            Pageable pageable) {

        String orgFilter = organizer.orElse("");
        String tagFilter = tag.orElse("");
        boolean hasFromTo = from.isPresent() && to.isPresent();

        if (hasFromTo && !organizer.isPresent() && !tag.isPresent()) {
            return eventRepository.findByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(from.get(), to.get(), pageable);
        } else if (!hasFromTo && organizer.isPresent() && !tag.isPresent()) {
            return eventRepository.findByOrganizerContainingIgnoreCase(orgFilter, pageable);
        } else if (!hasFromTo && !organizer.isPresent() && tag.isPresent()) {
            return eventRepository.findByTagsContainingIgnoreCase(tagFilter, pageable);
        } else if (!hasFromTo && organizer.isPresent() && tag.isPresent()) {
            return eventRepository.findByOrganizerContainingIgnoreCaseAndTagsContainingIgnoreCase(orgFilter, tagFilter, pageable);
        } else if (hasFromTo && organizer.isPresent() && !tag.isPresent()) {
            return eventRepository.findByOrganizerContainingIgnoreCaseAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(orgFilter, from.get(), to.get(), pageable);
        } else if (hasFromTo && !organizer.isPresent() && tag.isPresent()) {
            return eventRepository.findByTagsContainingIgnoreCaseAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(tagFilter, from.get(), to.get(), pageable);
        } else if (hasFromTo && organizer.isPresent() && tag.isPresent()) {
            return eventRepository.findByOrganizerContainingIgnoreCaseAndTagsContainingIgnoreCaseAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(orgFilter, tagFilter, from.get(), to.get(), pageable);
        } else {
            return eventRepository.findAll(pageable);
        }
    }

    @Transactional(readOnly = true)
    public Event get(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Event not found: " + id));
    }

    @Transactional
    public Event update(Long id, EventDto dto) {
        validateDates(dto.getStartTime(), dto.getEndTime());
        Event existing = get(id);
        // Ensure capacity is not reduced below current attendance
        long current = attendeeRepository.countByEvent(existing);
        if (dto.getCapacity() < current) {
            throw new BadRequestException("Capacity cannot be less than current attendance (" + current + ")");
        }
        eventMapper.updateEntity(existing, dto);
        Event saved = eventRepository.save(existing);
        log.info("Updated event id={}", saved.getId());
        return saved;
    }

    @Transactional
    public void delete(Long id) {
        Event existing = get(id);
        eventRepository.delete(existing);
        log.info("Deleted event id={}", id);
    }

    private void validateDates(OffsetDateTime start, OffsetDateTime end) {
        if (start == null || end == null) return;
        if (end.isBefore(start) || end.isEqual(start)) {
            throw new BadRequestException("End time must be after start time");
        }
    }
}
