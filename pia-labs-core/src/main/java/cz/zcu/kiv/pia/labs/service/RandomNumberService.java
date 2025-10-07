package cz.zcu.kiv.pia.labs.service;

import org.springframework.stereotype.Service;

import java.util.random.RandomGenerator;

/**
 * Implementation of {@link NumberService} returning random numbers.
 */
// register RandomNumberService as a Spring bean named "myRandomNumberService"
@Service("myRandomNumberService")
public class RandomNumberService implements NumberService {
    private final RandomGenerator randomGenerator;

    // inject RandomGenerator instance via constructor-based dependency injection
    public RandomNumberService(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public Number getNumber() {
        return randomGenerator.nextLong();
    }
}
