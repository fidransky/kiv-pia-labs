package cz.zcu.kiv.pia.labs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.random.RandomGenerator;

@Configuration
public class CoreConfiguration {
    // register RandomGenerator as a Spring bean named "randomGenerator" (see method name)
    @Bean
    public RandomGenerator randomGenerator() {
        return RandomGenerator.getDefault();
    }
}
