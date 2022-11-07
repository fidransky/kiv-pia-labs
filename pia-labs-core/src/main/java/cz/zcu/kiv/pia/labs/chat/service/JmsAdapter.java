package cz.zcu.kiv.pia.labs.chat.service;

import cz.zcu.kiv.pia.labs.chat.event.MessageSentEvent;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class JmsAdapter {
    private static final Logger LOG = getLogger(JmsAdapter.class);

    @EventListener(classes = MessageSentEvent.class)
    public void onMessageSent(MessageSentEvent event) {
        LOG.info(event.message().text());
    }
}
