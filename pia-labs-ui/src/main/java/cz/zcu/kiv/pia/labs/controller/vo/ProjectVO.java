package cz.zcu.kiv.pia.labs.controller.vo;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

/**
 * Value Object for Project data in the view layer
 */
public record ProjectVO(
    UUID id,
    UserVO customer,
    UserVO translator,
    Locale targetLanguage,
    String state,
    Instant createdAt,
    boolean hasSourceFile,
    boolean hasTranslatedFile
) {
}
