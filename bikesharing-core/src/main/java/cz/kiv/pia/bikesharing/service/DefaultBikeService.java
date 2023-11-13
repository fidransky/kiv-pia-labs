package cz.kiv.pia.bikesharing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.domain.Location;
import cz.kiv.pia.bikesharing.exception.BikeNotServiceableException;
import cz.kiv.pia.bikesharing.repository.BikeRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import jakarta.jms.Message;
import java.time.Period;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class DefaultBikeService implements BikeService {
    private static final Logger LOG = getLogger(DefaultBikeService.class);

    private final BikeRepository bikeRepository;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final Period serviceInterval;
    private final Map<UUID, List<Consumer<Location>>> locationSubscribers;

    public DefaultBikeService(BikeRepository bikeRepository, @Qualifier("jmsTopicTemplate") JmsTemplate jmsTemplate, ObjectMapper objectMapper, Period serviceInterval) {
        this.bikeRepository = bikeRepository;
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
        this.serviceInterval = serviceInterval;
        this.locationSubscribers = new ConcurrentHashMap<>();
    }

    @Override
    public Collection<Bike> getRideableBikes() {
        LOG.info("Getting rideable bikes");

        return bikeRepository.getRideableBikes();
    }

    @Override
    public Collection<Bike> getBikesDueForService() {
        LOG.info("Getting bikes due for service");

        return bikeRepository.getBikesDueForService();
    }

    @Override
    public void markServiced(Bike bike) {
        LOG.info("Marking bike {} serviced", bike);

        var existingBike = bikeRepository.getById(bike.getId());

        LOG.debug("Bike {} exists", existingBike);

        if (!existingBike.isDueForService(serviceInterval)) {
            LOG.info("Bike {} is not due for service", existingBike);

            throw new BikeNotServiceableException(existingBike);
        }

        existingBike.markServiced();

        LOG.debug("Bike {} is mark serviced, saving it", existingBike);

        bikeRepository.save(existingBike);
    }

    @Override
    public void moveBike(UUID bikeId, Location location) {
        try {
            var destination = "kiv.pia.bikesharing.bikes." + bikeId.toString() + ".location";
            var body = objectMapper.writeValueAsString(new BikeLocationDTO(location));

            jmsTemplate.convertAndSend(destination, body);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void watchBikeLocation(UUID bikeId, Consumer<Location> subscriber) {
        if (!locationSubscribers.containsKey(bikeId)) {
            locationSubscribers.put(bikeId, new ArrayList<>());
        }
        locationSubscribers.get(bikeId).add(subscriber);
    }

    @JmsListener(destination = "kiv.pia.bikesharing.bikes.*.location", containerFactory = "jmsTopicListenerFactory")
    public void updateBikeLocation(Message message) {
        try {
            var destination = message.getJMSDestination().toString();
            var bikeId = UUID.randomUUID(); // TODO: parse bikeId from destination
            var locationDTO = objectMapper.readValue(message.getBody(String.class), BikeLocationDTO.class);

            for (Consumer<Location> subscriber : locationSubscribers.getOrDefault(bikeId, Collections.emptyList())) {
                try {
                    var location = new Location(locationDTO.longitude(), locationDTO.latitude());
                    subscriber.accept(location);
                } catch (Exception e) {
                    locationSubscribers.get(bikeId).remove(subscriber);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Bike location update transmitted through MQ.
     *
     * @param longitude Longitude in defined coordinates system.
     * @param latitude Latitude in defined coordinates system.
     */
    private record BikeLocationDTO(
            Double longitude,
            Double latitude
    ) {
        public BikeLocationDTO(Location location) {
            this(location.getLongitude(), location.getLatitude());
        }
    }
}
