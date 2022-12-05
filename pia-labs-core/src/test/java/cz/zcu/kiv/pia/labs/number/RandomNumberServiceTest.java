package cz.zcu.kiv.pia.labs.number;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RandomNumberServiceTest {
    private final NumberService numberService = new RandomNumberService(new Random());

    @Test
    void getNumber() {
        var number = numberService.getNumber();
        assertNotEquals(number, numberService.getNumber());
    }
}
