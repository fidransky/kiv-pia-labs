package cz.zcu.kiv.pia.labs.chat.service;

import cz.zcu.kiv.pia.labs.chat.domain.Room;
import cz.zcu.kiv.pia.labs.chat.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private RoomService roomService;

    @Test
    void searchRooms_withoutName() {
        var room = new Room("running", UserService.DEFAULT_USER);

        when(roomRepository.findAll()).thenReturn(Flux.just(room));

        var result = roomService.searchRooms("").collectList().block();

        assertEquals(1, result.size());
        assertEquals(room, result.get(0));

        verify(roomRepository).findAll();
        verifyNoMoreInteractions(roomRepository);
    }

    @Test
    void searchRooms_withName() {
        var room = new Room("running", UserService.DEFAULT_USER);

        when(roomRepository.findByName(room.getName())).thenReturn(Flux.just(room));

        var result = roomService.searchRooms(room.getName()).collectList().block();

        assertEquals(1, result.size());
        assertEquals(room, result.get(0));

        verify(roomRepository).findByName(room.getName());
        verifyNoMoreInteractions(roomRepository);
    }
}
