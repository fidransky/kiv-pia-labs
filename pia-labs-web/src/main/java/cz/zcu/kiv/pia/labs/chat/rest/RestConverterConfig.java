package cz.zcu.kiv.pia.labs.chat.rest;

import cz.zcu.kiv.pia.labs.chat.rest.converter.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class RestConverterConfig implements WebFluxConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        var userToUserVOConverter = new UserToUserVOConverter();
        var userVOToUserConverter = new UserVOToUserConverter();
        var roomToRoomVOConverter = new RoomToRoomVOConverter(userToUserVOConverter);
        var messageToMessageVOConverter = new MessageToMessageVOConverter(userToUserVOConverter);
        var messageVOToMessageConverter = new MessageVOToMessageConverter();

        registry.addConverter(userToUserVOConverter);
        registry.addConverter(userVOToUserConverter);
        registry.addConverter(roomToRoomVOConverter);
        registry.addConverter(messageToMessageVOConverter);
        registry.addConverter(messageVOToMessageConverter);
    }
}
