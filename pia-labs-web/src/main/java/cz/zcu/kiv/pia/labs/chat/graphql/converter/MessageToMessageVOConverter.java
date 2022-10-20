package cz.zcu.kiv.pia.labs.chat.graphql.converter;

import cz.zcu.kiv.pia.labs.chat.domain.Message;
import cz.zcu.kiv.pia.labs.chat.graphql.MessageVO;
import org.springframework.core.convert.converter.Converter;

import java.time.ZoneOffset;

public class MessageToMessageVOConverter implements Converter<Message, MessageVO> {
    private final UserToUserVOConverter userConverter;

    public MessageToMessageVOConverter(UserToUserVOConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public MessageVO convert(Message source) {
        return MessageVO.builder()
                .withId(source.id())
                .withText(source.text())
                .withTimestamp(source.timestamp().atOffset(ZoneOffset.UTC))
                .withAuthor(userConverter.convert(source.author()))
                .build();
    }
}
