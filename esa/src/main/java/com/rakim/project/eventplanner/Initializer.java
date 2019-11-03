package com.rakim.project.eventplanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.rakim.project.eventplanner.model.Plan;
import com.rakim.project.eventplanner.model.PlanRepository;
import com.rakim.project.eventplanner.model.Event;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final PlanRepository repository;

    public Initializer(PlanRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Meeting", "Gaming", "Cinema",
                "Ultimate Frisbee").forEach(name ->
                repository.save(new Plan(name))
        );

        Plan meeting = repository.findByName("Meeting");
        Event e = Event.builder().title("Promotion")
                .description("In opposite land")
                .date(Instant.parse("2019-11-04T11:50:00.000Z"))
                .build();
        meeting.setEvents(Collections.singleton(e));
        repository.save(meeting);

        repository.findAll().forEach(System.out::println);
    }
}