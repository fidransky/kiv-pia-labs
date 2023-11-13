package cz.kiv.pia.bikesharing.controller;

import cz.kiv.pia.bikesharing.domain.Location;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.function.Consumer;

/**
 * SSE-based implementation of bike location subscriber.
 */
class BikeLocationSubscriber implements Consumer<Location> {
    private final SseEmitter sseEmitter;

    public BikeLocationSubscriber(SseEmitter sseEmitter) {
        this.sseEmitter = sseEmitter;
    }

    @Override
    public void accept(Location location) {
        try {
            var event = SseEmitter.event()
                    .name("bikeLocation")
                    .data(location)
                    .build();

            sseEmitter.send(event);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
