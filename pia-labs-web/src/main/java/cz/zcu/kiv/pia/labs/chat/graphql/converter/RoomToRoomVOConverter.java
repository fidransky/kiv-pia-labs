package cz.zcu.kiv.pia.labs.chat.graphql.converter;

import cz.zcu.kiv.pia.labs.chat.domain.Room;
import cz.zcu.kiv.pia.labs.chat.graphql.RoomVO;
import org.springframework.core.convert.converter.Converter;

public class RoomToRoomVOConverter implements Converter<Room, RoomVO> {
    private final UserToUserVOConverter userConverter;

    public RoomToRoomVOConverter(UserToUserVOConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public RoomVO convert(Room source) {
        return RoomVO.builder()
                .withId(source.getId())
                .withName(source.getName())
                .withAdministrator(userConverter.convert(source.getAdministrator()))
                .build();
    }
}
