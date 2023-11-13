package cz.kiv.pia.bikesharing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import jakarta.jms.ConnectionFactory;

@Configuration
public class JmsConfiguration {
    @Bean
    public DefaultJmsListenerContainerFactory jmsQueueListenerFactory(ConnectionFactory connectionFactory) {
        var factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);
        return factory;
    }

    @Bean
    public JmsTemplate jmsQueueTemplate(ConnectionFactory connectionFactory) {
        var jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(false);
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsTopicListenerFactory(ConnectionFactory connectionFactory) {
        var factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTopicTemplate(ConnectionFactory connectionFactory) {
        var jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }
}
