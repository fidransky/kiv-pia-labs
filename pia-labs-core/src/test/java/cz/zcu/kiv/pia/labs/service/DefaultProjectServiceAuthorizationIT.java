package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.CoreConfiguration;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import cz.zcu.kiv.pia.labs.repository.ProjectRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import static cz.zcu.kiv.pia.labs.service.DefaultProjectServiceAuthorizationIT.Configuration.ADMINISTRATOR_USER;
import static cz.zcu.kiv.pia.labs.service.DefaultProjectServiceAuthorizationIT.Configuration.CUSTOMER_USER;
import static cz.zcu.kiv.pia.labs.service.DefaultProjectServiceAuthorizationIT.Configuration.TRANSLATOR_USER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration tests of {@link ProjectService} method security.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {
        CoreConfiguration.class,
        DefaultProjectServiceAuthorizationIT.Configuration.class,
})
class DefaultProjectServiceAuthorizationIT {
    @MockitoBean
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @Nested
    class createProject {
        @Test
        @WithMockUser
        void noRole() {
            assertThrows(AccessDeniedException.class, () -> projectService.createProject(Locale.CHINESE, new byte[0]));
        }

        @Test
        @WithUserDetails(CUSTOMER_USER)
        void customer() {
            assertDoesNotThrow(() -> projectService.createProject(Locale.CHINESE, new byte[0]));
        }

        @Test
        @WithUserDetails(TRANSLATOR_USER)
        void translator() {
            assertThrows(AccessDeniedException.class, () -> projectService.createProject(Locale.CHINESE, new byte[0]));
        }

        @Test
        @WithUserDetails(ADMINISTRATOR_USER)
        void administrator() {
            assertThrows(AccessDeniedException.class, () -> projectService.createProject(Locale.CHINESE, new byte[0]));
        }
    }

    @TestConfiguration
    static class Configuration {
        static final String CUSTOMER_USER = "john.doe@example.com";
        static final String TRANSLATOR_USER = "jane.doe@example.com";
        static final String ADMINISTRATOR_USER = "pavel.fidransky@yoso.fi";

        @Primary
        @Bean
        public UserDetailsService userDetailsService() {
            var customer = User.createCustomer("John Doe", CUSTOMER_USER);
            var translator = User.createTranslator("Jane Doe", TRANSLATOR_USER, Set.of(Locale.FRENCH, Locale.GERMAN));
            var administrator = new User(UUID.randomUUID(), "Pavel Fidranský", ADMINISTRATOR_USER, UserRole.ADMINISTRATOR, Collections.emptySet(), Instant.now());

            return new InMemoryUserDetailsManager(customer, translator, administrator);
        }
    }
}
