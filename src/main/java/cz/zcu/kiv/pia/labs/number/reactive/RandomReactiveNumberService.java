package cz.zcu.kiv.pia.labs.number.reactive;

import org.slf4j.Logger;
import reactor.core.publisher.Mono;

import java.util.random.RandomGenerator;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implementation of {@link ReactiveNumberService} returning random numbers.
 */
public class RandomReactiveNumberService implements ReactiveNumberService {
    private static final Logger LOG = getLogger(RandomReactiveNumberService.class);
    private final RandomGenerator randomGenerator;

    public RandomReactiveNumberService(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
        LOG.debug(getClass().getSimpleName() + " initialized");
    }

    @Override
    public Mono<Number> getNumber() {
        return Mono.just(randomGenerator.nextLong());
    }
}
