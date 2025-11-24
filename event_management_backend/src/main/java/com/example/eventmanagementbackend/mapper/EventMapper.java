package com.example.eventmanagementbackend.mapper;

import com.example.eventmanagementbackend.dto.EventDto;
import com.example.eventmanagementbackend.dto.EventResponseDto;
import com.example.eventmanagementbackend.model.Event;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event toEntity(EventDto dto) {
        Event e = new Event();
        e.setTitle(dto.getTitle());
        e.setDescription(dto.getDescription());
        e.setStartTime(dto.getStartTime());
        e.setEndTime(dto.getEndTime());
        e.setLocation(dto.getLocation());
        e.setCapacity(dto.getCapacity());
        e.setOrganizer(dto.getOrganizer());
        e.setTags(tagsToString(dto.getTags()));
        return e;
    }

    public void updateEntity(Event e, EventDto dto) {
        e.setTitle(dto.getTitle());
        e.setDescription(dto.getDescription());
        e.setStartTime(dto.getStartTime());
        e.setEndTime(dto.getEndTime());
        e.setLocation(dto.getLocation());
        e.setCapacity(dto.getCapacity());
        e.setOrganizer(dto.getOrganizer());
        e.setTags(tagsToString(dto.getTags()));
    }

    public EventResponseDto toResponse(Event e) {
        EventResponseDto r = new EventResponseDto();
        r.setId(e.getId());
        r.setTitle(e.getTitle());
        r.setDescription(e.getDescription());
        r.setStartTime(e.getStartTime());
        r.setEndTime(e.getEndTime());
        r.setLocation(e.getLocation());
        r.setCapacity(e.getCapacity());
        r.setOrganizer(e.getOrganizer());
        r.setTags(stringToTags(e.getTags()));
        r.setAttendanceCount(e.getAttendanceCount());
        return r;
    }

    private String tagsToString(List<String> tags) {
        if (tags == null || tags.isEmpty()) return null;
        return tags.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(","));
    }

    private List<String> stringToTags(String tags) {
        if (tags == null || tags.isBlank()) return Collections.emptyList();
        return Arrays.stream(tags.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
