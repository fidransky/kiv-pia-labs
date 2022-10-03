package cz.zcu.kiv.pia.labs;

import cz.zcu.kiv.pia.labs.number.ConstantNumberService;
import cz.zcu.kiv.pia.labs.number.NumberService;
import cz.zcu.kiv.pia.labs.number.RandomNumberService;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Random;
import java.util.random.RandomGenerator;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "cz.zcu.kiv.pia.labs")
public class WebAppConfig implements WebMvcConfigurer {
    private static final Logger LOG = getLogger(WebAppConfig.class);

    @EventListener
    public void onContextStarted(ContextStartedEvent cse) {
        LOG.info("*************** APPLICATION CONTEXT STARTED ***************");

        for (String beanName : cse.getApplicationContext().getBeanDefinitionNames()) {
            LOG.info(beanName);
        }
    }

    @Bean
    public RandomGenerator randomGenerator() {
        return RandomGenerator.getDefault();
    }

    @Bean
    public NumberService randomNumberService(RandomGenerator randomGenerator) {
        return new RandomNumberService(randomGenerator);
    }

    @Bean
    public NumberService constantNumberService() {
        return new ConstantNumberService(666L);
    }
}
