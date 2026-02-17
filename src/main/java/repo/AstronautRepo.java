package repo;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Astronaut;

import java.nio.file.Path;

public class AstronautRepo extends AbstractRepoImpl<Astronaut, Integer> {

    public AstronautRepo(Path filePath) {
        super(
                filePath,
                Astronaut::getId,
                new TypeReference<java.util.List<Astronaut>>() {}
        );
    }
}