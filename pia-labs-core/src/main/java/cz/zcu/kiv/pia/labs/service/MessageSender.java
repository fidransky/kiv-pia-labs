package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Project;

/**
 * Service used to send messages from the app.
 */
public interface MessageSender {
    /**
     * Sends a message indicating that the given project has been completed.
     *
     * @param project the project that has been completed
     */
    void sendProjectCompletedMessage(Project project);
}
