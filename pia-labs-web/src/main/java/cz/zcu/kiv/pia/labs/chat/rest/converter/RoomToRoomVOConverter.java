package cz.zcu.kiv.pia.labs.chat.rest.converter;

import cz.zcu.kiv.pia.labs.chat.domain.Room;
import cz.zcu.kiv.pia.labs.chat.rest.model.RoomVO;
import org.springframework.core.convert.converter.Converter;

public class RoomToRoomVOConverter implements Converter<Room, RoomVO> {
    private final UserToUserVOConverter userConverter;

    public RoomToRoomVOConverter(UserToUserVOConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public RoomVO convert(Room source) {
        return new RoomVO()
                .id(source.getId())
                .name(source.getName())
                .administrator(userConverter.convert(source.getAdministrator()));
    }
}
