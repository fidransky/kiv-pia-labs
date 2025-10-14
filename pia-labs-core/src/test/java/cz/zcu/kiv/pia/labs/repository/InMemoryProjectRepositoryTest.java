package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    class getAll {
        @Test
        void shouldReturnZeroProjects() {
            List<Project> projects = projectRepository.getAll();

            assertTrue(projects.isEmpty());
        }

        @Test
        void shouldReturnAllProjects() {
            User customer = User.createCustomer("John Doe", "john.doe@example.com");
            Project project1 = customer.createProject(Locale.ENGLISH, "test content".getBytes());
            projectRepository.store(project1);

            Project project2 = customer.createProject(Locale.GERMAN, "test content".getBytes());
            projectRepository.store(project2);

            List<Project> projects = projectRepository.getAll();

            assertFalse(projects.isEmpty());
            assertTrue(projects.contains(project1));
            assertTrue(projects.contains(project2));
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
