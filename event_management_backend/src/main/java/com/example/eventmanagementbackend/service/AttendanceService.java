package com.example.eventmanagementbackend.service;

import com.example.eventmanagementbackend.dto.AttendeeRegistrationDto;
import com.example.eventmanagementbackend.exception.ConflictException;
import com.example.eventmanagementbackend.exception.NotFoundException;
import com.example.eventmanagementbackend.mapper.AttendeeMapper;
import com.example.eventmanagementbackend.model.Attendee;
import com.example.eventmanagementbackend.model.Event;
import com.example.eventmanagementbackend.repository.AttendeeRepository;
import com.example.eventmanagementbackend.repository.EventRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendanceService {

    private static final Logger log = LoggerFactory.getLogger(AttendanceService.class);

    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;
    private final AttendeeMapper attendeeMapper;

    public AttendanceService(EventRepository eventRepository, AttendeeRepository attendeeRepository, AttendeeMapper attendeeMapper) {
        this.eventRepository = eventRepository;
        this.attendeeRepository = attendeeRepository;
        this.attendeeMapper = attendeeMapper;
    }

    @Transactional
    public Attendee register(Long eventId, AttendeeRegistrationDto dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found: " + eventId));

        long count = attendeeRepository.countByEvent(event);
        if (event.getCapacity() != null && count >= event.getCapacity()) {
            throw new ConflictException("Event at capacity");
        }

        attendeeRepository.findByEventAndEmailIgnoreCase(event, dto.getEmail())
                .ifPresent(a -> { throw new ConflictException("Attendee already registered with this email"); });

        Attendee a = attendeeMapper.toEntity(dto, event);
        Attendee saved = attendeeRepository.save(a);
        log.info("Registered attendee id={} to event id={}", saved.getId(), eventId);
        return saved;
    }

    @Transactional
    public void unregister(Long eventId, Long attendeeId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found: " + eventId));
        Attendee attendee = attendeeRepository.findById(attendeeId)
                .orElseThrow(() -> new NotFoundException("Attendee not found: " + attendeeId));
        if (!attendee.getEvent().getId().equals(event.getId())) {
            throw new NotFoundException("Attendee does not belong to event");
        }
        attendeeRepository.delete(attendee);
        log.info("Unregistered attendee id={} from event id={}", attendeeId, eventId);
    }

    @Transactional(readOnly = true)
    public List<Attendee> list(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found: " + eventId));
        return attendeeRepository.findByEvent(event);
    }

    @Transactional(readOnly = true)
    public long count(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found: " + eventId));
        return attendeeRepository.countByEvent(event);
    }
}
