package com.example.eventmanagementbackend.config;

import com.example.eventmanagementbackend.model.Attendee;
import com.example.eventmanagementbackend.model.Event;
import com.example.eventmanagementbackend.repository.AttendeeRepository;
import com.example.eventmanagementbackend.repository.EventRepository;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner seed(EventRepository events, AttendeeRepository attendees) {
        return args -> {
            if (events.count() > 0) return;

            Event e1 = new Event(
                    "Tech Conference 2025",
                    "A conference about modern technologies.",
                    OffsetDateTime.now().plusDays(10),
                    OffsetDateTime.now().plusDays(10).plusHours(8),
                    "San Francisco, CA",
                    3,
                    "ACME Events",
                    "tech,conference"
            );
            Event e2 = new Event(
                    "Design Summit",
                    "Explore modern design systems.",
                    OffsetDateTime.now().plusDays(20),
                    OffsetDateTime.now().plusDays(20).plusHours(6),
                    "New York, NY",
                    2,
                    "DesignOrg",
                    "design,uiux"
            );
            events.save(e1);
            events.save(e2);

            attendees.save(new Attendee("Alice", "alice@example.com", e1));
            attendees.save(new Attendee("Bob", "bob@example.com", e1));

            log.info("Seeded sample events and attendees");
        };
    }
}
