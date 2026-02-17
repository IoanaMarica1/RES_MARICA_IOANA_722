package service;

import model.Astronaut;
import model.AstronautStatus;
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
                .forEach(x-> System.out.println("[#"+x.getId()+"] "+x.getName()+" | "+x.getSpacecraft()+" | "+x.getStatus()+" | "+"exp="+x.getExperienceLevel()));

    }
    public void filterAstronauts(String d){
        astronautsRepo.findAll()
                .stream()
                .filter(x->x.getSpacecraft().equalsIgnoreCase(d))
                .filter(x->x.getStatus().equals(AstronautStatus.ACTIVE))
                .forEach(x-> System.out.println("[#"+x.getId()+"] "+x.getName()+" | "+x.getSpacecraft()+" | "+x.getStatus()+" | "+"exp="+x.getExperienceLevel()));
    }
    public void sortAstronauts(){
        astronautsRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(Astronaut::getExperienceLevel).reversed().thenComparing(Astronaut::getName))
                .forEach(x-> System.out.println("[#"+x.getId()+"] "+x.getName()+" | "+x.getSpacecraft()+" | "+x.getStatus()+" | "+"exp="+x.getExperienceLevel()));

    }
    public void sortedFile(){
        try(PrintWriter writer = new PrintWriter(new FileWriter("astronauts_sorted.txt") )) {
            astronautsRepo.findAll().stream()
                    .sorted(Comparator.comparing(Astronaut::getExperienceLevel).reversed().thenComparing(Astronaut::getName))
                    .map(x->"[#"+x.getId()+"] "+x.getName()+" | "+x.getSpacecraft()+" | "+x.getStatus()+" | "+"exp="+x.getExperienceLevel())
                    .forEach(writer::println);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void pointsAstronaut(SupplyService supplyService, MissionEventService eventService){
        System.out.println("Top 5 Astronauts: "+"\n");
        record Ranking(String name, String spaceCraft, Integer score){

        }

        List<Ranking> rankingList = astronautsRepo.findAll().stream()
                .map(driver->{
                    Integer totalP = eventService.pointsAstronaut(driver.getId());
                    Integer totalC = supplyService.ammountSupply(driver.getId());
                    Integer score = totalC+totalP;
                    return new Ranking(driver.getName(), driver.getSpacecraft(), score);
                })
                .sorted(Comparator.comparing(Ranking::score).reversed().thenComparing(Ranking::name))
                .limit(5)
                .toList();
        for (int i = 0; i < rankingList.size(); i++) {
            Ranking r = rankingList.get(i);
            System.out.printf("%d. %s (%s) -> %d%n", i + 1, r.name(),r.spaceCraft(), r.score());
        }
    }
    public void pointsAstronaut1(SupplyService supplyService, MissionEventService eventService){
        System.out.println("Leading SpaceCraft: ");
        record Ranking(String name, String spaceCraft, Integer score){

        }
        astronautsRepo.findAll().stream()
                .map(d->{
                    Integer totalP = eventService.pointsAstronaut(d.getId());
                    Integer totalC = supplyService.ammountSupply(d.getId());
                    Integer score = totalC+totalP;
                    return new Ranking(d.getName(), d.getSpacecraft(), score);
                })
                .sorted(Comparator.comparing(Ranking::score).reversed().thenComparing(Ranking::name))
                .limit(1)
                .forEach(e->System.out.printf("%s%n", e.spaceCraft()));

    }

}