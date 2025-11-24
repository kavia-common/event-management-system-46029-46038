package com.example.eventmanagementbackend.controller;

import com.example.eventmanagementbackend.dto.AttendeeRegistrationDto;
import com.example.eventmanagementbackend.dto.AttendeeResponseDto;
import com.example.eventmanagementbackend.mapper.AttendeeMapper;
import com.example.eventmanagementbackend.model.Attendee;
import com.example.eventmanagementbackend.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events/{eventId}/attendees")
@Tag(name = "Attendance", description = "Attendee registration and management")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendeeMapper attendeeMapper;

    public AttendanceController(AttendanceService attendanceService, AttendeeMapper attendeeMapper) {
        this.attendanceService = attendanceService;
        this.attendeeMapper = attendeeMapper;
    }

    // PUBLIC_INTERFACE
    @PostMapping
    @Operation(summary = "Register attendee", description = "Register an attendee to an event. Enforces capacity and prevents duplicates.")
    public ResponseEntity<AttendeeResponseDto> register(@PathVariable Long eventId, @RequestBody @Valid AttendeeRegistrationDto dto) {
        Attendee a = attendanceService.register(eventId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(attendeeMapper.toResponse(a));
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{attendeeId}")
    @Operation(summary = "Unregister attendee", description = "Remove an attendee from an event")
    public ResponseEntity<Void> unregister(@PathVariable Long eventId, @PathVariable Long attendeeId) {
        attendanceService.unregister(eventId, attendeeId);
        return ResponseEntity.noContent().build();
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List attendees", description = "List attendees for an event")
    public ResponseEntity<List<AttendeeResponseDto>> list(@PathVariable Long eventId) {
        List<AttendeeResponseDto> list = attendanceService.list(eventId).stream()
                .map(attendeeMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // PUBLIC_INTERFACE
    @GetMapping("/count")
    @Operation(summary = "Attendance count", description = "Get current attendance count for an event")
    public ResponseEntity<Long> count(@PathVariable Long eventId) {
        return ResponseEntity.ok(attendanceService.count(eventId));
    }
}
