package cz.zcu.kiv.pia.labs.service;

//import org.springframework.stereotype.Service;

import java.util.random.RandomGenerator;

/**
 * Implementation of {@link NumberService} returning random numbers.
 */
// annotation-based bean definition
// we define beans explicitly in BeanConfiguration instead
//@Service("randomNumberService")
public class RandomNumberService implements NumberService {
    private final RandomGenerator randomGenerator;

    public RandomNumberService(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public Number getNumber() {
        return randomGenerator.nextLong();
    }
}
