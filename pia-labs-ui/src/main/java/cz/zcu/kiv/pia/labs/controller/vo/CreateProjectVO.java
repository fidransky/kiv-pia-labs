package cz.zcu.kiv.pia.labs.controller.vo;

import org.springframework.web.multipart.MultipartFile;
import java.util.Locale;

/**
 * Value Object for creating new translation projects
 */
public record CreateProjectVO(
    String targetLanguage,
    MultipartFile sourceFile
) {
    /**
     * Converts string language code to Locale
     */
    public Locale getTargetLanguageAsLocale() {
        return targetLanguage != null && !targetLanguage.isEmpty() ? 
            Locale.forLanguageTag(targetLanguage) : null;
    }
}
