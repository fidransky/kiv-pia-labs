package cz.zcu.kiv.pia.labs.number.reactive;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RandomReactiveNumberServiceTest {
    private final ReactiveNumberService numberService = new RandomReactiveNumberService(new Random());

    @Test
    void getNumber() {
        var number = numberService.getNumber().block();
        assertNotEquals(number, numberService.getNumber().block());
    }
}
