package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultProjectServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private ProjectRepository projectRepository;

    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        this.projectService = new DefaultProjectService(userService, projectRepository);
    }

    @Nested
    class createProject {
        @Test
        void shouldCreateAndStoreProject() {
            // test data
            var customerUser = User.createCustomer("John Doe", "john.doe@example.com");
            var targetLanguage = Locale.FRENCH;
            var sourceFile = "test content".getBytes();

            // mocks
            when(userService.getCurrentUser()).thenReturn(customerUser);

            // tested method
            Project createdProject = projectService.createProject(targetLanguage, sourceFile);

            // verifications
            assertNotNull(createdProject);
            assertEquals(customerUser.getId(), createdProject.getCustomer().getId());
            assertEquals(targetLanguage, createdProject.getTargetLanguage());
            assertArrayEquals(sourceFile, createdProject.getSourceFile());

            verify(userService).getCurrentUser();
            verify(projectRepository).store(createdProject);
            verifyNoMoreInteractions(userService, projectRepository);
        }

        @Test
        void shouldThrowIllegalStateException() {
            // test data
            var translatorUser = User.createTranslator("John Doe", "john.doe@example.com", Collections.singleton(Locale.FRENCH));
            var targetLanguage = Locale.FRENCH;
            var sourceFile = "test content".getBytes();

            // mocks
            when(userService.getCurrentUser()).thenReturn(translatorUser);

            // tested method
            assertThrows(IllegalStateException.class, () -> projectService.createProject(targetLanguage, sourceFile));

            // verifications
            verify(userService).getCurrentUser();
            verifyNoMoreInteractions(userService, projectRepository);
        }
    }

    @Nested
    class getProjects {
        @Test
        void shouldReturnAllProjects() {
            // test data
            User customer = User.createCustomer("John Doe", "john.doe@example.com");
            Project project1 = customer.createProject(Locale.ENGLISH, "test content".getBytes());
            Project project2 = customer.createProject(Locale.GERMAN, "test content".getBytes());

            // mocks
            when(projectRepository.getAll()).thenReturn(List.of(project1, project2));

            // tested method
            List<Project> projects = projectRepository.getAll();

            // verifications
            assertFalse(projects.isEmpty());
            assertTrue(projects.contains(project1));
            assertTrue(projects.contains(project2));

            verify(projectRepository).getAll();
            verifyNoMoreInteractions(userService, projectRepository);
        }
    }
}
