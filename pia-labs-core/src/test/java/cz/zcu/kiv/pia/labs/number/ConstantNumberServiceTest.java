package cz.zcu.kiv.pia.labs.number;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstantNumberServiceTest {
    private final Number CONSTANT_NUMBER = 666L;
    private final NumberService numberService = new ConstantNumberService(CONSTANT_NUMBER);

    @Test
    void getNumber() {
        assertEquals(CONSTANT_NUMBER, numberService.getNumber());
    }
}
