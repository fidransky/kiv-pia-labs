package cz.zcu.kiv.pia.labs;

import cz.zcu.kiv.pia.labs.repository.ProjectRepository;
import cz.zcu.kiv.pia.labs.service.DefaultProjectService;
import cz.zcu.kiv.pia.labs.service.ProjectService;
import cz.zcu.kiv.pia.labs.service.SpringSecurityUserService;
import cz.zcu.kiv.pia.labs.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import java.util.random.RandomGenerator;

@Configuration
public class CoreConfiguration {
    // register RandomGenerator as a Spring bean named "randomGenerator" (see method name)
    @Bean
    public RandomGenerator randomGenerator() {
        return RandomGenerator.getDefault();
    }

    @Bean
    public SecurityContextHolderStrategy contextHolderStrategy() {
        return SecurityContextHolder.getContextHolderStrategy();
    }

    @Bean
    public UserService userService(SecurityContextHolderStrategy contextHolderStrategy) {
        return new SpringSecurityUserService(contextHolderStrategy);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        var provider = new DaoAuthenticationProvider(userDetailsService);
        // use plaintext password encoder, NOT ready for production use!
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }

    @Bean
    public ProjectService projectService(UserService userService, ProjectRepository projectRepository, ApplicationEventPublisher eventPublisher) {
        return new DefaultProjectService(userService, projectRepository, eventPublisher);
    }
}
