package com.example.eventmanagementbackend.mapper;

import com.example.eventmanagementbackend.dto.AttendeeRegistrationDto;
import com.example.eventmanagementbackend.dto.AttendeeResponseDto;
import com.example.eventmanagementbackend.model.Attendee;
import com.example.eventmanagementbackend.model.Event;
import org.springframework.stereotype.Component;

@Component
public class AttendeeMapper {

    public Attendee toEntity(AttendeeRegistrationDto dto, Event event) {
        Attendee a = new Attendee();
        a.setName(dto.getName());
        a.setEmail(dto.getEmail());
        a.setEvent(event);
        return a;
    }

    public AttendeeResponseDto toResponse(Attendee a) {
        AttendeeResponseDto r = new AttendeeResponseDto();
        r.setId(a.getId());
        r.setName(a.getName());
        r.setEmail(a.getEmail());
        return r;
    }
}
