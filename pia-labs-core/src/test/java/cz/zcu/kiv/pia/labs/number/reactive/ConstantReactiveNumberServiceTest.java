package cz.zcu.kiv.pia.labs.number.reactive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstantReactiveNumberServiceTest {
    private final Number CONSTANT_NUMBER = 666L;
    private final ReactiveNumberService numberService = new ConstantReactiveNumberService(CONSTANT_NUMBER);

    @Test
    void getNumber() {
        assertEquals(CONSTANT_NUMBER, numberService.getNumber().block());
    }
}
