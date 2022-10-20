package cz.zcu.kiv.pia.labs.chat.graphql.converter;

import cz.zcu.kiv.pia.labs.chat.domain.Message;
import cz.zcu.kiv.pia.labs.chat.graphql.MessageVO;
import cz.zcu.kiv.pia.labs.chat.service.UserService;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.util.UUID;

public class MessageVOToMessageConverter implements Converter<MessageVO, Message> {
    @Override
    public Message convert(MessageVO source) {
        return new Message(UUID.randomUUID(), source.getText(), Instant.now(), UserService.DEFAULT_USER);
    }
}
