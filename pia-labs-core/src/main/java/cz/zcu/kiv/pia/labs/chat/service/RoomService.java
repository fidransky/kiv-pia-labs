package cz.zcu.kiv.pia.labs.chat.service;

import cz.zcu.kiv.pia.labs.chat.domain.Message;
import cz.zcu.kiv.pia.labs.chat.domain.Room;
import cz.zcu.kiv.pia.labs.chat.domain.User;
import cz.zcu.kiv.pia.labs.chat.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class RoomService {
    public static final Room DEFAULT_ROOM = new Room("default", UserService.DEFAULT_USER);

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Searches chat rooms by (partial) name or returns all when name is blank.
     *
     * @param name Searched chat room name
     * @return Found chat rooms
     */
    public Flux<Room> searchRooms(String name) {
        if (StringUtils.hasLength(name)) {
            return roomRepository.findByName(name);
        } else {
            return roomRepository.findAll();
        }
    }

    /**
     * Creates a new chat room.
     *
     * @param name Name of the new chat room
     * @param administrator Administrator of the new chat room
     * @return Created chat room
     */
    public Mono<Room> createRoom(String name, User administrator) {
        return Mono.just(new Room(name, administrator))
                .doOnNext(roomRepository::createRoom);
    }

    /**
     * Retrieves chat room by its identifier
     *
     * @param id Unique chat room identifier
     * @return Found chat room
     */
    public Mono<Room> getRoom(UUID id) {
        return roomRepository.findById(id);
    }

    /**
     * Retrieves chat room users by chat room identifier
     *
     * @param id Unique chat room identifier
     * @return Chat room users
     */
    public Flux<User> getRoomUsers(UUID id) {
        return roomRepository.findById(id)
                .map(Room::getUsers)
                .flatMapMany(Flux::fromIterable);
    }

    /**
     * Retrieves chat room messages by chat room identifier
     *
     * @param id Unique chat room identifier
     * @return Chat room messages
     */
    public Flux<Message> getRoomMessages(UUID id) {
        return roomRepository.findById(id)
                .map(Room::getMessages)
                .flatMapMany(Flux::fromIterable);
    }

    /**
     * Joins user to chat room with given identifier
     *
     * @param id Unique chat room identifier
     * @param user User joining chat room
     * @return Void
     */
    public Mono<Room> joinRoom(UUID id, User user) {
        return roomRepository.findById(id)
                .doOnNext(room -> room.join(user));
    }

    /**
     * Sends message to chat room with given identifier
     *
     * @param id Unique chat room identifier
     * @param message Message sent to chat room
     * @return Chat room
     */
    public Mono<Room> sendMessageToRoom(UUID id, Message message) {
        return roomRepository.findById(id)
                .doOnNext(room -> room.sendMessage(message));
    }
}
