package cz.zcu.kiv.pia.labs.chat.converter.rest;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import cz.zcu.kiv.pia.labs.chat.rest.model.UserVO;
import org.springframework.core.convert.converter.Converter;

public class UserToUserVOConverter implements Converter<User, UserVO> {
    @Override
    public UserVO convert(User source) {
        return new UserVO()
                .id(source.getId())
                .username(source.getUsername());
    }
}
