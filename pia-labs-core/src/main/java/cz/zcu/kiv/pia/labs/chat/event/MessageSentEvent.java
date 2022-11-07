package cz.zcu.kiv.pia.labs.chat.event;

import cz.zcu.kiv.pia.labs.chat.domain.Message;
import cz.zcu.kiv.pia.labs.chat.domain.Room;

public record MessageSentEvent(Room room, Message message) {
}
