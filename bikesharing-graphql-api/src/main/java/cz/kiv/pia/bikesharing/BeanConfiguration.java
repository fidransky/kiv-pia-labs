package cz.kiv.pia.bikesharing;

import graphql.scalars.ExtendedScalars;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.Period;

@Configuration
public class BeanConfiguration {

    @Bean
    public Period serviceInterval(@Value("${bike.service-interval}") int serviceInterval) {
        return Period.ofMonths(serviceInterval);
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.DateTime);
    }
}
