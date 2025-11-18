package cz.zcu.kiv.pia.labs;

import cz.zcu.kiv.pia.labs.repository.InMemoryProjectRepository;
import cz.zcu.kiv.pia.labs.repository.ProjectRepository;
import cz.zcu.kiv.pia.labs.service.DefaultProjectService;
import cz.zcu.kiv.pia.labs.service.MockUserService;
import cz.zcu.kiv.pia.labs.service.ProjectService;
import cz.zcu.kiv.pia.labs.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.random.RandomGenerator;

@Configuration
public class CoreConfiguration {
    // register RandomGenerator as a Spring bean named "randomGenerator" (see method name)
    @Bean
    public RandomGenerator randomGenerator() {
        return RandomGenerator.getDefault();
    }

    @Bean
    public UserService userService() {
        return new MockUserService();
    }

    @Bean
    public ProjectRepository projectRepository() {
        return new InMemoryProjectRepository();
    }

    @Bean
    public ProjectService projectService(UserService userService, ProjectRepository projectRepository, ApplicationEventPublisher eventPublisher) {
        return new DefaultProjectService(userService, projectRepository, eventPublisher);
    }
}
