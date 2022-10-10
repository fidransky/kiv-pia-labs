package cz.zcu.kiv.pia.labs.number;

import org.slf4j.Logger;

import java.util.random.RandomGenerator;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implementation of {@link NumberService} returning random numbers.
 */
public class RandomNumberService implements NumberService {
    private static final Logger LOG = getLogger(RandomNumberService.class);
    private final RandomGenerator randomGenerator;

    public RandomNumberService(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
        LOG.debug(getClass().getSimpleName() + " initialized");
    }

    @Override
    public Number getNumber() {
        return randomGenerator.nextLong();
    }
}
