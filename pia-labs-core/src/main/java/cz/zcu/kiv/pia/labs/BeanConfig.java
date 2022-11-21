package cz.zcu.kiv.pia.labs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

@Configuration
public class BeanConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new JsonMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .registerModule(new JavaTimeModule());
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer applicationPropertiesConfigurer(@Value("classpath:application.properties") Resource applicationProperties) {
        var configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocations(applicationProperties);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }
}
