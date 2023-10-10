package cz.kiv.pia.bikesharing.controller;

import cz.kiv.pia.bikesharing.model.LocationDTO;
import cz.kiv.pia.bikesharing.model.StandDTO;
import cz.kiv.pia.bikesharing.service.StandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StandController implements StandsApi {
    private final StandService standService;

    public StandController(StandService standService) {
        this.standService = standService;
    }

    @Override
    public ResponseEntity<List<StandDTO>> retrieveStands() {
        var result = standService.getAll();

        List<StandDTO> dtos = new ArrayList<>();
        for (var stand : result) {
            var locationDTO = new LocationDTO()
                    .latitude(stand.getLocation().getLatitude())
                    .longitude(stand.getLocation().getLongitude());

            var standDTO = new StandDTO()
                    .id(stand.getId().toString())
                    .name(stand.getName())
                    .location(locationDTO);

            dtos.add(standDTO);
        }

        return ResponseEntity.ok(dtos);
    }
}
