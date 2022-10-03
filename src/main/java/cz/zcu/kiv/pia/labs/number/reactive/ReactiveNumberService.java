package cz.zcu.kiv.pia.labs.number.reactive;

import reactor.core.publisher.Mono;

public interface ReactiveNumberService {
    /**
     * @return a single number, either random or constant
     */
    Mono<Number> getNumber();
}
