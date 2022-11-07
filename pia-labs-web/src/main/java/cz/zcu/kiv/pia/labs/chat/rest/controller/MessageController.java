package cz.zcu.kiv.pia.labs.chat.rest.controller;

import cz.zcu.kiv.pia.labs.chat.domain.Message;
import cz.zcu.kiv.pia.labs.chat.rest.api.MessagesApi;
import cz.zcu.kiv.pia.labs.chat.rest.model.MessageVO;
import cz.zcu.kiv.pia.labs.chat.service.RoomService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@RestController
public class MessageController implements MessagesApi {
    private final RoomService roomService;
    private final ConversionService conversionService;

    public MessageController(RoomService roomService, ConversionService conversionService) {
        this.roomService = roomService;
        this.conversionService = conversionService;
    }

    @Override
    public ResponseEntity<List<MessageVO>> getRoomMessages(UUID roomId) {
        var result = roomService.getRoomMessages(roomId)
                .map(room -> conversionService.convert(room, MessageVO.class))
                .collectList()
                .block();

        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/v1/rooms/{roomId}/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessageVO> streamRoomMessages(@PathVariable("roomId") UUID roomId) {
        return roomService.streamRoomMessages(roomId)
                .map(room -> conversionService.convert(room, MessageVO.class));
    }

    @Override
    public ResponseEntity<Void> sendMessage(UUID roomId, MessageVO messageVO) {
        roomService.sendMessageToRoom(roomId, conversionService.convert(messageVO, Message.class))
                .subscribe();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
