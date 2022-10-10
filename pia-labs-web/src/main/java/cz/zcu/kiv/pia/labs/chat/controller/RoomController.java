package cz.zcu.kiv.pia.labs.chat.controller;

import cz.zcu.kiv.pia.labs.chat.rest.api.RoomsApi;
import cz.zcu.kiv.pia.labs.chat.rest.model.RoomVO;
import cz.zcu.kiv.pia.labs.chat.service.RoomService;
import cz.zcu.kiv.pia.labs.chat.service.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

// java.lang.IllegalStateException: No primary or default constructor found for interface org.springframework.web.server.ServerWebExchange error:
// https://stackoverflow.com/a/51350483
@RestController
public class RoomController implements RoomsApi {

    private final RoomService roomService;
    private final ConversionService conversionService;

    public RoomController(RoomService roomService, ConversionService conversionService) {
        this.roomService = roomService;
        this.conversionService = conversionService;
    }

    @Override
    public ResponseEntity<RoomVO> createRoom(RoomVO roomVO) {
        var name = roomVO.getName();

        var result = roomService.createRoom(name, UserService.DEFAULT_USER)
                .map(room -> conversionService.convert(room, RoomVO.class))
                .block();

        URI uri = UriComponentsBuilder.fromPath("/v1/rooms/{roomId}").build(result.getId().toString());
        return ResponseEntity.created(uri).body(result);
    }

    @Override
    public ResponseEntity<RoomVO> getRoom(UUID roomId) {
        var result = roomService.getRoom(roomId)
                .map(room -> conversionService.convert(room, RoomVO.class))
                .block();

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<RoomVO>> listRooms(String name) {
        var result = roomService.searchRooms(name)
                .map(room -> conversionService.convert(room, RoomVO.class))
                .collectList()
                .block();

        return ResponseEntity.ok(result);
    }

    /*
    @Override
    public Mono<ResponseEntity<RoomVO>> createRoom(Mono<RoomVO> roomVO, ServerWebExchange exchange) {
        var in = roomVO.block();

        var out = roomService.createRoom(in.getName(), conversionService.convert(in.getAdministrator(), User.class));

        return out.map(room -> {
            URI uri = UriComponentsBuilder.fromPath("/v1/rooms/{roomId}").build(room.getId().toString());
            return ResponseEntity.created(uri).body(conversionService.convert(room, RoomVO.class));
        });
    }

    @Override
    public Mono<ResponseEntity<RoomVO>> getRoom(UUID roomId, ServerWebExchange exchange) {
        var out = roomService.getRoom(roomId);

        return out.map(room -> ResponseEntity.ok(conversionService.convert(room, RoomVO.class)));
    }

    @Override
    public Mono<ResponseEntity<Flux<RoomVO>>> listRooms(String name, ServerWebExchange exchange) {
        var out = roomService.searchRooms(name);

        return Mono.just(ResponseEntity.ok(out.map(room -> conversionService.convert(room, RoomVO.class))));
    }
     */
}
