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
    public void top5MissionEvents() {
        eventsRepo.findAll().stream()
                .sorted(Comparator.comparing(MissionEvent::getId))
                .map(e->{
                    Integer computingPoints = switch(e.getType()){
                        case EVA -> e.getBasePoints()+2*e.getDay();
                        case SYSTEM_FAILURE ->  e.getBasePoints()-3-e.getDay();
                        case SCIENCE ->  e.getBasePoints()+(e.getDay()%4);
                        case MEDICAL -> e.getBasePoints()-2*(e.getDay()%3);
                        case COMMUNICATION -> e.getBasePoints()+5;

                    };
                    return String.format("Event "+e.getId()+"-> raw="+e.getBasePoints()+" ->computed="+computingPoints);
                }).limit(5)
                .forEach(System.out::println);
    }
    public Integer pointsAstronaut(Integer id){
        return eventsRepo.findAll()
                .stream()
                .filter(x->x.getAstronautId().equals(id))
                .mapToInt(e->{
                    return switch(e.getType()){
                        case EVA -> e.getBasePoints()+2*e.getDay();
                        case SYSTEM_FAILURE ->  e.getBasePoints()-3-e.getDay();
                        case SCIENCE ->  e.getBasePoints()+(e.getDay()%4);
                        case MEDICAL -> e.getBasePoints()-2*(e.getDay()%3);
                        case COMMUNICATION -> e.getBasePoints()+5;
                    };
                })
                .sum();
    }
}