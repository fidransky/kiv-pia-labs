package cz.zcu.kiv.pia.labs;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication
@ServletComponentScan
// this annotation makes Spring Framework scan classpath for plain Java servlets, filters and listeners too
public class Application {

    private static final Logger LOG = getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // prints all registered beans whenever application context is refreshed - most commonly on application startup
    @EventListener
    public void onContextRefreshed(ContextRefreshedEvent event) {
        LOG.info("*************** APPLICATION CONTEXT REFRESHED ***************");

        for (String beanName : event.getApplicationContext().getBeanDefinitionNames()) {
            LOG.info(beanName);
        }
    }
}
