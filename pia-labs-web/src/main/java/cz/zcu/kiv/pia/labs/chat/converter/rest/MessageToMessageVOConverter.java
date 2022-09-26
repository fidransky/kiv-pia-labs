package cz.zcu.kiv.pia.labs.chat.converter.rest;

import cz.zcu.kiv.pia.labs.chat.domain.Message;
import cz.zcu.kiv.pia.labs.chat.rest.model.MessageVO;
import org.springframework.core.convert.converter.Converter;

import java.time.ZoneOffset;

public class MessageToMessageVOConverter implements Converter<Message, MessageVO> {
    private final UserToUserVOConverter userConverter;

    public MessageToMessageVOConverter(UserToUserVOConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public MessageVO convert(Message source) {
        return new MessageVO()
                .id(source.id())
                .text(source.text())
                .timestamp(source.timestamp().atOffset(ZoneOffset.UTC))
                .author(userConverter.convert(source.author()));
    }
}
