package cz.zcu.kiv.pia.labs;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.zcu.kiv.pia.labs.number.reactive.ConstantReactiveNumberService;
import cz.zcu.kiv.pia.labs.number.reactive.RandomReactiveNumberService;
import cz.zcu.kiv.pia.labs.number.reactive.ReactiveNumberService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.random.RandomGenerator;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableWebFlux
@ComponentScan(basePackages = "cz.zcu.kiv.pia.labs")
public class WebAppConfig implements WebFluxConfigurer {
    private static final Logger LOG = getLogger(WebAppConfig.class);

    @Autowired
    private ObjectMapper objectMapper;

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
    public ReactiveNumberService randomNumberService(RandomGenerator randomGenerator) {
        return new RandomReactiveNumberService(randomGenerator);
    }

    @Bean
    public ReactiveNumberService constantNumberService() {
        return new ConstantReactiveNumberService(666L);
    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/v1/**")
                .allowedOrigins("http://localhost:3000");

        registry.addMapping("/graphql")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods(HttpMethod.POST.name());
    }
}
