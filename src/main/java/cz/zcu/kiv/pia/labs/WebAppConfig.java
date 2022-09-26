package cz.zcu.kiv.pia.labs;

import org.slf4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "cz.zcu.kiv.pia.labs")
public class WebAppConfig implements WebMvcConfigurer {
    private static final Logger LOG = getLogger(WebAppConfig.class);

    @EventListener
    public void onContextStarted(ContextStartedEvent cse) {
        LOG.info("*************** APPLICATION CONTEXT STARTED ***************");

        for (String beanName : cse.getApplicationContext().getBeanDefinitionNames()) {
            LOG.info(beanName);
        }
    }
}
