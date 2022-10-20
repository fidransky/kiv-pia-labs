package cz.zcu.kiv.pia.labs.chat.graphql.controller;

import cz.zcu.kiv.pia.labs.chat.domain.Message;
import cz.zcu.kiv.pia.labs.chat.graphql.MessageVO;
import cz.zcu.kiv.pia.labs.chat.graphql.RoomVO;
import cz.zcu.kiv.pia.labs.chat.graphql.UserVO;
import cz.zcu.kiv.pia.labs.chat.service.RoomService;
import cz.zcu.kiv.pia.labs.chat.service.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Controller
public class GraphQLController {
    private final RoomService roomService;
    private final UserService userService;
    private final ConversionService conversionService;

    public GraphQLController(RoomService roomService, UserService userService, ConversionService conversionService) {
        this.roomService = roomService;
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @SchemaMapping(typeName = "Query", value = "listRooms")
    public Flux<RoomVO> listRooms(@Argument String name) {
        return roomService.searchRooms(name)
                .map(room -> conversionService.convert(room, RoomVO.class));
    }

    @QueryMapping
    public Mono<RoomVO> getRoom(@Argument UUID id) {
        return roomService.getRoom(id)
                .map(room -> conversionService.convert(room, RoomVO.class));
    }

    @BatchMapping(typeName = "RoomVO", value = "users")
    public Flux<List<UserVO>> loadRoomUsers(@Argument List<RoomVO> roomVOs) {
        return Flux.fromIterable(roomVOs)
                .flatMapSequential(roomVO -> roomService.getRoomUsers(roomVO.getId()).map(user -> conversionService.convert(user, UserVO.class)).collectList());
    }

    @BatchMapping(typeName = "RoomVO", value = "messages")
    public Flux<List<MessageVO>> loadRoomMessages(@Argument List<RoomVO> roomVOs) {
        return Flux.fromIterable(roomVOs)
                .flatMapSequential(roomVO -> roomService.getRoomMessages(roomVO.getId()).map(message -> conversionService.convert(message, MessageVO.class)).collectList());
    }

    @MutationMapping
    public Mono<RoomVO> createRoom(@Argument String name) {
        return roomService.createRoom(name, UserService.DEFAULT_USER)
                .map(room -> conversionService.convert(room, RoomVO.class));
    }

    @MutationMapping
    public Mono<UserVO> registerUser(@Argument String username) {
        return userService.registerUser(username)
                .map(user -> conversionService.convert(user, UserVO.class));
    }

    @MutationMapping
    public Mono<RoomVO> joinRoom(@Argument UUID roomId) {
        return roomService.joinRoom(roomId, UserService.DEFAULT_USER)
                .map(room -> conversionService.convert(room, RoomVO.class));
    }

    @MutationMapping
    public Mono<RoomVO> sendMessage(@Argument UUID roomId, @Argument String text) {
        return roomService.sendMessageToRoom(roomId, new Message(UUID.randomUUID(), text, Instant.now(), UserService.DEFAULT_USER))
                .map(room -> conversionService.convert(room, RoomVO.class));
    }

    @SubscriptionMapping
    public Flux<MessageVO> streamRoomMessages(@Argument UUID roomId) {
        return roomService.getRoomMessages(roomId)
                .map(room -> conversionService.convert(room, MessageVO.class));
    }
}
