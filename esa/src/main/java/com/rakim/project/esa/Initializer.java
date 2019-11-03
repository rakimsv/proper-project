package com.rakim.project.esa;

import com.rakim.project.esa.model.Event;
import com.rakim.project.esa.model.Group;
import com.rakim.project.esa.model.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final GroupRepository repository;

    public Initializer(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Presentation", "Gaming", "Cinema",
                "Ultimate Frisbee", "Climbing").forEach(name ->
                repository.save(new Group(name))
        );

        Group pres = repository.findByName("Presentation");
        Event e = Event.builder().title("Breaking the 4th wall")
                .description("Presenting meetup planner")
                .date(Instant.parse("2019-11-04T11:50:00.000Z"))
                .build();
        pres.setEvents(Collections.singleton(e));
        repository.save(pres);

        repository.findAll().forEach(System.out::println);
    }
}