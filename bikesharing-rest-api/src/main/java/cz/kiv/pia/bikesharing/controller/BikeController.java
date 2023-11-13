package cz.kiv.pia.bikesharing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kiv.pia.bikesharing.model.LocationDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.jms.Message;
import java.io.IOException;
import java.util.*;

@RestController
public class BikeController implements BikesApi {
    private final Map<UUID, List<SseEmitter>> emitters = new HashMap<>();

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public BikeController(@Qualifier("jmsTopicTemplate") JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<Void> moveBike(UUID bikeId, LocationDTO locationDTO) {
        try {
            var destination = "kiv.pia.bikesharing.bikes." + bikeId.toString() + ".location";
            var body = objectMapper.writeValueAsString(locationDTO);

            jmsTemplate.convertAndSend(destination, body);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @JmsListener(destination = "kiv.pia.bikesharing.bikes.*.location", containerFactory = "jmsTopicListenerFactory")
    public void processMessage(Message message) {
        try {
            var destination = message.getJMSDestination().toString();
            var bikeId = UUID.randomUUID(); // TODO: parse from destination
            var locationDTO = objectMapper.readValue(message.getBody(String.class), LocationDTO.class);

            var event = SseEmitter.event()
                    .name("bikeLocation")
                    .data(locationDTO)
                    .build();

            for (SseEmitter emitter : emitters.getOrDefault(bikeId, Collections.emptyList())) {
                try {
                    emitter.send(event);
                } catch (IOException e) {
                    emitters.get(bikeId).remove(emitter);
                }
            }

        } catch (Exception e) {
            // TODO: handle the exception
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/bikes/{bikeId}/location", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamBikeLocation(@PathVariable("bikeId") UUID bikeId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        if (!emitters.containsKey(bikeId)) {
            emitters.put(bikeId, new ArrayList<>());
        }
        emitters.get(bikeId).add(emitter);

        return emitter;
    }
}
