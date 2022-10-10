package cz.zcu.kiv.pia.labs.chat.repository;

import cz.zcu.kiv.pia.labs.chat.domain.Room;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RoomRepository {
    /**
     * Creates a new chat room.
     *
     * @param room Chat room to be created
     * @return Created chat room
     */
    Mono<Room> createRoom(Room room);

    /**
     * Retrieves all chat rooms.
     *
     * @return Found chat rooms
     */
    Flux<Room> findAll();

    /**
     * Searches chat rooms by (partial) name.
     *
     * @param name Searched chat room name
     * @return Found chat rooms
     */
    Flux<Room> findByName(String name);

    /**
     * Retrieves chat room by its unique identifier
     *
     * @param id Unique chat room identifier
     * @return Found chat room
     */
    Mono<Room> findById(UUID id);
}
