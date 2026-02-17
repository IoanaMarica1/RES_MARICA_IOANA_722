package service;

import model.MissionEvent;
import repo.AbstractRepoImpl;
import repo.AbstractRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class MissionEventService {
    Path path = Path.of("data/events.json");

    AbstractRepository<MissionEvent, Integer> eventsRepo =
            new AbstractRepoImpl<>(
                    path,
                    MissionEvent::getId,
                    new com.fasterxml.jackson.core.type.TypeReference<List<MissionEvent>>() {
                    }
            );

    public void countMissionEvents() {
        long value = eventsRepo.count();
        System.out.println("Events Loaded: " + value + "\n");
    }
}