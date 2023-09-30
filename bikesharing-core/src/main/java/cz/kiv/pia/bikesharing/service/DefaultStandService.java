package cz.kiv.pia.bikesharing.service;

import cz.kiv.pia.bikesharing.domain.Stand;
import cz.kiv.pia.bikesharing.repository.StandRepository;
import org.slf4j.Logger;

import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

public class DefaultStandService implements StandService {
    private static final Logger LOG = getLogger(DefaultStandService.class);

    private final StandRepository standRepository;

    public DefaultStandService(StandRepository standRepository) {
        this.standRepository = standRepository;
    }

    @Override
    public Collection<Stand> getAll() {
        LOG.info("Getting all stands");

        return standRepository.getAll();
    }
}
