package cz.zcu.kiv.pia.labs.chat.rest.controller;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import cz.zcu.kiv.pia.labs.chat.rest.api.UsersApi;
import cz.zcu.kiv.pia.labs.chat.rest.model.UserVO;
import cz.zcu.kiv.pia.labs.chat.service.RoomService;
import cz.zcu.kiv.pia.labs.chat.service.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController implements UsersApi {
    private final UserService userService;
    private final RoomService roomService;
    private final ConversionService conversionService;

    public UserController(UserService userService, RoomService roomService, ConversionService conversionService) {
        this.userService = userService;
        this.roomService = roomService;
        this.conversionService = conversionService;
    }

    @Override
    public ResponseEntity<UserVO> createUser(UserVO userVO) {
        var result = userService.registerUser(userVO.getUsername())
                .map(user -> conversionService.convert(user, UserVO.class))
                .block();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<List<UserVO>> getRoomUsers(UUID roomId) {
        var result = roomService.getRoomUsers(roomId)
                .map(user -> conversionService.convert(user, UserVO.class))
                .collectList()
                .block();

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> joinRoom(UUID roomId, UserVO userVO) {
        roomService.joinRoom(roomId, conversionService.convert(userVO, User.class))
                .subscribe();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
