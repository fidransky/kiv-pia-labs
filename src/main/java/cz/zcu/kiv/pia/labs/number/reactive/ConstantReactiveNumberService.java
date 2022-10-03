package cz.zcu.kiv.pia.labs.number.reactive;

import org.slf4j.Logger;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implementation of {@link ReactiveNumberService} returning a constant number set by constructor.
 */
public class ConstantReactiveNumberService implements ReactiveNumberService {
    private static final Logger LOG = getLogger(ConstantReactiveNumberService.class);

    private final Number number;

    public ConstantReactiveNumberService(Number number) {
        this.number = number;
        LOG.debug(getClass().getSimpleName() + " initialized");
    }

    @Override
    public Mono<Number> getNumber() {
        return Mono.just(number);
    }
}
