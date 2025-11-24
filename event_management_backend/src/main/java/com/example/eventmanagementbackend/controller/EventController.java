package com.example.eventmanagementbackend.controller;

import com.example.eventmanagementbackend.dto.EventDto;
import com.example.eventmanagementbackend.dto.EventResponseDto;
import com.example.eventmanagementbackend.mapper.EventMapper;
import com.example.eventmanagementbackend.model.Event;
import com.example.eventmanagementbackend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Events", description = "Event creation, read, update, delete and listing")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    // PUBLIC_INTERFACE
    @PostMapping
    @Operation(summary = "Create event", description = "Create a new event",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Validation or bad request")
            })
    public ResponseEntity<EventResponseDto> create(@RequestBody @Valid EventDto dto) {
        Event saved = eventService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventMapper.toResponse(saved));
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List events", description = "List events with optional date range, organizer, and tag filters")
    public ResponseEntity<Page<EventResponseDto>> list(
            @Parameter(description = "Start of date range (inclusive) ISO-8601") @RequestParam Optional<OffsetDateTime> from,
            @Parameter(description = "End of date range (inclusive) ISO-8601") @RequestParam Optional<OffsetDateTime> to,
            @Parameter(description = "Organizer filter (contains, case-insensitive)") @RequestParam Optional<String> organizer,
            @Parameter(description = "Tag filter (contains, case-insensitive)") @RequestParam Optional<String> tag,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort, e.g. startTime,asc") @RequestParam(defaultValue = "startTime,asc") String sort
    ) {
        Sort s = parseSort(sort);
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), s);
        Page<Event> result = eventService.list(from, to, organizer.map(String::trim).filter(v -> !v.isEmpty()), tag.map(String::trim).filter(v -> !v.isEmpty()), pageable);
        Page<EventResponseDto> mapped = result.map(eventMapper::toResponse);
        return ResponseEntity.ok(mapped);
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(summary = "Get event", description = "Retrieve an event by id")
    public ResponseEntity<EventResponseDto> get(@PathVariable Long id) {
        Event e = eventService.get(id);
        return ResponseEntity.ok(eventMapper.toResponse(e));
    }

    // PUBLIC_INTERFACE
    @PutMapping("/{id}")
    @Operation(summary = "Update event", description = "Update an event by id")
    public ResponseEntity<EventResponseDto> update(@PathVariable Long id, @RequestBody @Valid EventDto dto) {
        Event e = eventService.update(id, dto);
        return ResponseEntity.ok(eventMapper.toResponse(e));
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete event", description = "Delete an event by id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by(Sort.Order.asc("startTime"));
        String[] parts = sort.split(",");
        if (parts.length == 2 && "desc".equalsIgnoreCase(parts[1])) {
            return Sort.by(Sort.Order.desc(parts[0]));
        }
        return Sort.by(Sort.Order.asc(parts[0]));
    }
}
