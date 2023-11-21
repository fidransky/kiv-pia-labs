package cz.kiv.pia.bikesharing.repository.mapper;

import cz.kiv.pia.bikesharing.domain.Location;
import cz.kiv.pia.bikesharing.domain.Stand;
import cz.kiv.pia.bikesharing.repository.dto.StandDTO;

import java.util.function.Function;

public class StandMapper implements Function<StandDTO, Stand> {
    @Override
    public Stand apply(StandDTO standDTO) {
        var id = standDTO.getId();
        var name = standDTO.getName();
        var location = new Location(standDTO.getLocation().getLongitude(), standDTO.getLocation().getLatitude());
        return new Stand(id, name, location);
    }
}
