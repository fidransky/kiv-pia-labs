package cz.zcu.kiv.pia.labs.number;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implementation of {@link NumberService} returning a constant number set by constructor.
 */
public class ConstantNumberService implements NumberService {
    private static final Logger LOG = getLogger(ConstantNumberService.class);

    private final Number number;

    public ConstantNumberService(Number number) {
        this.number = number;
        LOG.debug(getClass().getSimpleName() + " initialized");
    }

    @Override
    public Number getNumber() {
        return number;
    }
}
