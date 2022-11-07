package cz.zcu.kiv.pia.labs.chat.service;

import cz.zcu.kiv.pia.labs.chat.domain.Message;
import cz.zcu.kiv.pia.labs.chat.event.MessageSentEvent;
import cz.zcu.kiv.pia.labs.chat.repository.RoomRepository;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Topic;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class JmsAdapter {
    private static final Logger LOG = getLogger(JmsAdapter.class);

    private final JmsTemplate jmsTemplate;
    private final RoomRepository roomRepository;

    public JmsAdapter(JmsTemplate jmsTemplate, RoomRepository roomRepository) {
        this.jmsTemplate = jmsTemplate;
        this.roomRepository = roomRepository;
    }

    @EventListener(classes = MessageSentEvent.class)
    public void onMessageSent(MessageSentEvent event) throws JmsException {
        LOG.info(event.message().text());
        jmsTemplate.convertAndSend(String.format("kiv.pia.chat.room.%s", event.room().getId()), event.message());
    }

    @JmsListener(destination = "kiv.pia.chat.room.*")
    public void onMessageReceived(@Header(JmsHeaders.DESTINATION) Topic destination, @Payload Message message) throws JMSException {
        String topicName = destination.getTopicName();
        UUID roomId = UUID.fromString(topicName.substring(topicName.lastIndexOf(".") + 1));

        roomRepository.findById(roomId)
                .subscribe(room -> room.streamMessages().tryEmitNext(message));
    }
}
