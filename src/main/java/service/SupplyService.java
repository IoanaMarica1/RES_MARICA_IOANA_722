package service;

import model.Supply;
import repo.AbstractRepoImpl;
import repo.AbstractRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class SupplyService {
    Path path = Path.of("data/supplies.json");

    AbstractRepository<Supply, Integer> supplyRepo =
            new AbstractRepoImpl<>(
                    path,
                    Supply::getId,
                    new com.fasterxml.jackson.core.type.TypeReference<List<Supply>>() {
                    }
            );

    public void countSupplys() {
        long value = supplyRepo.count();
        System.out.println("Supplies Loaded: " + value + "\n");
    }
    public Integer ammountSupply(Integer id){
       return  supplyRepo.findAll().stream()
                .filter(x->x.getAstronautId().equals(id))
                .mapToInt(Supply::getValue)
                .sum();
    }
}