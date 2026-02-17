package repo;

import com.fasterxml.jackson.core.type.TypeReference;
import model.MissionEvent;

import java.nio.file.Path;

public class MissionEventRepo extends AbstractRepoImpl<MissionEvent, Integer> {

    public MissionEventRepo(Path filePath) {
        super(
                filePath,
                MissionEvent::getId,
                new TypeReference<java.util.List<MissionEvent>>() {}
        );
    }
}