package cz.kiv.pia.bikesharing.service;

import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.exception.BikeNotServiceableException;
import cz.kiv.pia.bikesharing.repository.BikeRepository;

import java.time.LocalDate;
import java.util.Collection;

public class DefaultBikeService implements BikeService {
    private final BikeRepository bikeRepository;

    public DefaultBikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
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
        LocalDate twoMonthsAgo = LocalDate.now().minusMonths(2);

        if (!bike.getLastServiceTimestamp().isAfter(twoMonthsAgo)) {
            throw new BikeNotServiceableException(bike);
        }

        bike.markServiced();

        bikeRepository.saveBike(bike);
    }
}
