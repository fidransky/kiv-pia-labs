package cz.zcu.kiv.pia.labs.chat.repository;

import cz.zcu.kiv.pia.labs.chat.domain.Room;
import cz.zcu.kiv.pia.labs.chat.service.RoomService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class InMemoryRoomRepository implements RoomRepository {
    private final Map<UUID, Room> roomsMap;

    public InMemoryRoomRepository() {
        this.roomsMap = new HashMap<>();
    }

    @PostConstruct
    private void postConstruct() {
        roomsMap.put(RoomService.DEFAULT_ROOM.getId(), RoomService.DEFAULT_ROOM);
    }

    @Override
    public Mono<Room> createRoom(Room room) {
        roomsMap.put(room.getId(), room);

        return findById(room.getId());
    }

    @Override
    public Flux<Room> findAll() {
        var rooms = roomsMap.values();

        return Flux.fromIterable(rooms);
    }

    @Override
    public Flux<Room> findByName(String name) {
        var rooms = roomsMap.values()
                .stream()
                .filter(room -> room.getName().contains(name));

        return Flux.fromStream(rooms);
    }

    @Override
    public Mono<Room> findById(UUID id) {
        var room = roomsMap.get(id);

        return Mono.just(room);
    }
}
