package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProjectRepositoryTest {
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        this.projectRepository = new InMemoryProjectRepository();
    }

    @Nested
    class store {
        @Test
        void shouldStoreProject() {
            User customer = User.createCustomer("John Doe", "john.doe@example.com");
            Project project = customer.createProject(Locale.ENGLISH, "test content".getBytes());

            projectRepository.store(project);

            Project foundProject = projectRepository.findById(project.getId());
            assertNotNull(foundProject);
            assertEquals(project.getId(), foundProject.getId());
        }
    }

    @Nested
    class findById {
        @Test
        void shouldReturnNullWhenProjectNotFound() {
            UUID nonExistentId = UUID.randomUUID();

            Project foundProject = projectRepository.findById(nonExistentId);

            assertNull(foundProject);
        }

        @Test
        void shouldReturnStoredProject() {
            User customer = User.createCustomer("John Doe", "john.doe@example.com");
            Project project = customer.createProject(Locale.ENGLISH, "test content".getBytes());
            projectRepository.store(project);

            Project foundProject = projectRepository.findById(project.getId());

            assertNotNull(foundProject);
            assertEquals(project.getId(), foundProject.getId());
            assertEquals(Locale.ENGLISH, foundProject.getTargetLanguage());
            assertNotEquals(0, foundProject.getSourceFile().length);
        }
    }
}
