package cz.zcu.kiv.pia.labs.domain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests of {@link User} domain class.
 */
class UserTest {
    @Nested
    class createCustomer {
        @Test
        void success() {
            var user = User.createCustomer("John Doe", "john.doe@example.com");

            assertAll(() -> {
                assertNotNull(user.getId());
                assertEquals("John Doe", user.getName());
                assertEquals("john.doe@example.com", user.getEmailAddress());
                assertEquals(UserRole.CUSTOMER, user.getRole());
                assertTrue(user.getLanguages().isEmpty());
                assertNotNull(user.getCreatedAt());
            });
        }

        @ParameterizedTest
        @NullAndEmptySource
        // TODO: enable this test when name validation is implemented
        @Disabled("Not yet implemented")
        void invalidName(String name) {
            assertThrows(IllegalAccessException.class, () -> User.createCustomer(name, "john.doe@example.com"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = "not-a-valid-email-address")
        // TODO: enable this test when emailAddress validation is implemented
        @Disabled("Not yet implemented")
        void invalidEmailAddress(String emailAddress) {
            assertThrows(IllegalAccessException.class, () -> User.createCustomer("John Doe", emailAddress));
        }
    }

    @Nested
    class createTranslator {
        @Test
        void success() {
            var user = User.createTranslator("Jane Doe", "jane.doe@example.com", Set.of(Locale.GERMAN, Locale.FRENCH));

            assertAll(() -> {
                assertNotNull(user.getId());
                assertEquals("Jane Doe", user.getName());
                assertEquals("jane.doe@example.com", user.getEmailAddress());
                assertEquals(UserRole.TRANSLATOR, user.getRole());
                assertTrue(user.getLanguages().contains(Locale.GERMAN));
                assertTrue(user.getLanguages().contains(Locale.FRENCH));
                assertNotNull(user.getCreatedAt());
            });
        }

        // TODO: implement tests for unhappy scenarios
    }

    @Nested
    class createProject {
        @Test
        void success() {
            var user = User.createCustomer("John Doe", "john.doe@example.com");

            var project = user.createProject(Locale.CHINESE, "Hello World!".getBytes(StandardCharsets.UTF_8));

            assertAll(() -> {
                assertEquals(user, project.getCustomer());
                assertEquals(Locale.CHINESE, project.getTargetLanguage());
                assertNotNull(project.getSourceFile());
                assertNotNull(project.getCreatedAt());
            });
        }

        // TODO: implement tests for unhappy scenarios
    }
}