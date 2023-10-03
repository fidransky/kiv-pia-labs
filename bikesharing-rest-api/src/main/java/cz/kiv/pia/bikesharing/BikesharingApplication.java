package cz.kiv.pia.bikesharing;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication
public class BikesharingApplication {
    private static final Logger LOG = getLogger(BikesharingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BikesharingApplication.class, args);
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
