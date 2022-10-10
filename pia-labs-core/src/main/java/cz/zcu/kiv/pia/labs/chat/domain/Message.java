package cz.zcu.kiv.pia.labs.chat.domain;

import java.time.Instant;
import java.util.UUID;

public record Message(UUID id, String text, Instant timestamp, User author) {
}
