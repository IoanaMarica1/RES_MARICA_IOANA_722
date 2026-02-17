package service;

import model.Astronaut;
import repo.AbstractRepoImpl;
import repo.AbstractRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class AstronautService {
    Path path = Path.of("data/astronauts.json");

    AbstractRepository<Astronaut, Integer> astronautsRepo =
            new AbstractRepoImpl<>(
                    path,
                    Astronaut::getId,
                    new com.fasterxml.jackson.core.type.TypeReference<List<Astronaut>>() {
                    }
            );

    public void countAstronauts() {
        long value = astronautsRepo.count();
        System.out.println("Astronauts Loaded: " + value + "\n");
    }
    public void showAstronauts() {
        astronautsRepo.findAll()
                .stream()
                .forEach(x-> System.out.println("[#"+x.getId()+"] "+x.getName()+" | "+x.getSpacecraft()+" | "+x.getStatus()+" | "+"exp="+x.getExperienceLevel()+"\n"));

    }
}