package cz.zcu.kiv.pia.labs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import cz.zcu.kiv.pia.labs.deserializer.ByteArrayResourceDeserializer;
import cz.zcu.kiv.pia.labs.serializer.ByteArrayResourceSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Resource.class, new ByteArrayResourceDeserializer());
        module.addDeserializer(ByteArrayResource.class, new ByteArrayResourceDeserializer());
        module.addSerializer(ByteArrayResource.class, new ByteArrayResourceSerializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
