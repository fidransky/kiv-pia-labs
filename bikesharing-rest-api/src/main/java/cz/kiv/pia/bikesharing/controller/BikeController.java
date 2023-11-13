package cz.kiv.pia.bikesharing.controller;

import cz.kiv.pia.bikesharing.domain.Location;
import cz.kiv.pia.bikesharing.model.LocationDTO;
import cz.kiv.pia.bikesharing.service.BikeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RestController
public class BikeController implements BikesApi {
    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @Override
    public ResponseEntity<Void> moveBike(UUID bikeId, LocationDTO locationDTO) {
        try {
            var location = new Location(locationDTO.getLongitude(), locationDTO.getLatitude());
            bikeService.moveBike(bikeId, location);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/bikes/{bikeId}/location", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamBikeLocation(@PathVariable("bikeId") UUID bikeId) {
        var emitter = new SseEmitter(Long.MAX_VALUE);

        var subscriber = new BikeLocationSubscriber(emitter);
        bikeService.watchBikeLocation(bikeId, subscriber);

        return emitter;
    }
}
