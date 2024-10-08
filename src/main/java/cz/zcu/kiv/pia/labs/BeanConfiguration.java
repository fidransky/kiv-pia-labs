package cz.zcu.kiv.pia.labs;

import cz.zcu.kiv.pia.labs.repository.DamageRepository;
import cz.zcu.kiv.pia.labs.repository.InMemoryDamageRepository;
import cz.zcu.kiv.pia.labs.service.ConstantNumberService;
import cz.zcu.kiv.pia.labs.service.DamageService;
import cz.zcu.kiv.pia.labs.service.DefaultDamageService;
import cz.zcu.kiv.pia.labs.service.MockUserService;
import cz.zcu.kiv.pia.labs.service.NumberService;
import cz.zcu.kiv.pia.labs.service.RandomNumberService;
import cz.zcu.kiv.pia.labs.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Random;
import java.util.random.RandomGenerator;

// all bean definitions come here
@Configuration
public class BeanConfiguration {
    @Bean
    public RandomGenerator randomGenerator() {
        return new Random();
    }

    @Primary
    @Bean
    public NumberService randomNumberService(RandomGenerator randomGenerator) {
        return new RandomNumberService(randomGenerator);
    }

    @Bean
    public NumberService constantNumberService(@Value("${number.constant}") Number number) {
        return new ConstantNumberService(number);
    }

    @Bean
    public UserService userService() {
        return new MockUserService();
    }

    @Bean
    public DamageRepository damageRepository() {
        return new InMemoryDamageRepository();
    }

    @Bean
    public DamageService damageService(UserService userService, DamageRepository damageRepository) {
        return new DefaultDamageService(userService, damageRepository);
    }
}
