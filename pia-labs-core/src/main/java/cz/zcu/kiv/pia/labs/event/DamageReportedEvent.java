package cz.zcu.kiv.pia.labs.event;

import cz.zcu.kiv.pia.labs.domain.Damage;

/**
 * Application event published when a new {@link Damage} is reported and stored correctly.
 */
public record DamageReportedEvent(Damage damage) {
}
