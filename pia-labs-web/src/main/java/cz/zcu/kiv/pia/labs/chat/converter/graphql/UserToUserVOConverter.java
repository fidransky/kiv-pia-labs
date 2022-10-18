package cz.zcu.kiv.pia.labs.chat.converter.graphql;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import cz.zcu.kiv.pia.labs.chat.graphql.UserVO;
import org.springframework.core.convert.converter.Converter;

public class UserToUserVOConverter implements Converter<User, UserVO> {
    @Override
    public UserVO convert(User source) {
        return UserVO.builder()
                .withId(source.getId())
                .withUsername(source.getUsername())
                .build();
    }
}
