package cz.zcu.kiv.pia.labs.controller.vo;

import java.time.Instant;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

/**
 * Value Object for User data in the view layer
 */
public record UserVO(
    UUID id,
    String name,
    String emailAddress,
    String role,
    Set<Locale> languages,
    Instant createdAt
) {
}
