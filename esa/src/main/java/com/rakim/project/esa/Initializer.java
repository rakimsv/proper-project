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
        Stream.of("XtremeKings ES", "Mighty Lionz ES", "Serpico ES",
                "SUFC ES").forEach(name ->
                repository.save(new Group(name))
        );

        Group sufces = repository.findByName("SUFC ES");
        Event e = Event.builder().title("Season kick-off!")
                .description("First match of the season for the Knuts!")
                .date(Instant.parse("2019-11-09T20:00:00.000Z"))
                .build();
        sufces.setEvents(Collections.singleton(e));
        repository.save(sufces);

        repository.findAll().forEach(System.out::println);
    }
}