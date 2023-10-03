package cz.kiv.pia.bikesharing.service;

import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.exception.BikeNotServiceableException;
import cz.kiv.pia.bikesharing.repository.BikeRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class DefaultBikeService implements BikeService {
    private static final Logger LOG = getLogger(DefaultBikeService.class);

    private final BikeRepository bikeRepository;
    private final Period serviceInterval;

    public DefaultBikeService(BikeRepository bikeRepository, Period serviceInterval) {
        this.bikeRepository = bikeRepository;
        this.serviceInterval = serviceInterval;
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
}
