package repo;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Supply;

import java.nio.file.Path;

public class SupplyRepo extends AbstractRepoImpl<Supply, Integer> {

    public SupplyRepo(Path filePath) {
        super(
                filePath,
                Supply::getId,
                new TypeReference<java.util.List<Supply>>() {}
        );
    }
}