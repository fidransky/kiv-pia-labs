package cz.kiv.pia.bikesharing.service;

import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.exception.BikeNotServiceableException;
import cz.kiv.pia.bikesharing.repository.BikeRepository;

import java.time.Period;
import java.util.Collection;

public class DefaultBikeService implements BikeService {
    private final BikeRepository bikeRepository;
    private final Period serviceInterval;

    public DefaultBikeService(BikeRepository bikeRepository, Period serviceInterval) {
        this.bikeRepository = bikeRepository;
        this.serviceInterval = serviceInterval;
    }

    @Override
    public Collection<Bike> getRideableBikes() {
        return bikeRepository.getRideableBikes();
    }

    @Override
    public Collection<Bike> getBikesDueForService() {
        return bikeRepository.getBikesDueForService();
    }

    @Override
    public void markServiced(Bike bike) {
        var existingBike = bikeRepository.getById(bike.getId());

        if (!existingBike.isDueForService(serviceInterval)) {
            throw new BikeNotServiceableException(existingBike);
        }

        existingBike.markServiced();

        bikeRepository.save(existingBike);
    }
}
