package cz.zcu.kiv.pia.labs.chat.service;

import cz.zcu.kiv.pia.labs.chat.event.MessageSentEvent;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class JmsAdapter {
    private static final Logger LOG = getLogger(JmsAdapter.class);

    private final JmsTemplate jmsTemplate;

    public JmsAdapter(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @EventListener(classes = MessageSentEvent.class)
    public void onMessageSent(MessageSentEvent event) throws JmsException {
        LOG.info(event.message().text());
        jmsTemplate.convertAndSend(String.format("kiv.pia.chat.room.%s", event.room().getId()), event.message());
    }
}
