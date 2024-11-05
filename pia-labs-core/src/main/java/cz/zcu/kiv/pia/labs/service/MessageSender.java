package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.User;

import java.util.UUID;

/**
 * Service used to send messages from the app.
 */
public interface MessageSender {
    /**
     * Sends a message about newly reported damage to the impaired user.
     *
     * @param impaired Impaired user
     * @param damageId Damage identifier
     */
    void sendReportedDamageMessage(User impaired, UUID damageId);
}
