package cz.zcu.kiv.pia.labs.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsConfig {
    // Create ActiveMQ connection factory using localhost:61616 (JMS) as broker URL
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    // Create ActiveMQ connection pool
    @Bean(destroyMethod = "stop")
    public ConnectionFactory connectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        return new PooledConnectionFactory(activeMQConnectionFactory);
    }

    // Configure Spring JmsTemplate to use pub-sub (topic) messaging strategy
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        var jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }

    // Serialize message content to json using TextMessage
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
