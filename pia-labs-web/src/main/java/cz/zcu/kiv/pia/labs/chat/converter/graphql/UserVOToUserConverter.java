package cz.zcu.kiv.pia.labs.chat.converter.graphql;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import cz.zcu.kiv.pia.labs.chat.graphql.UserVO;
import org.springframework.core.convert.converter.Converter;

public class UserVOToUserConverter implements Converter<UserVO, User> {
    @Override
    public User convert(UserVO source) {
        return new User(source.getId(), source.getUsername());
    }
}
