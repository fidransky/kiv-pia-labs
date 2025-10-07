package cz.zcu.kiv.pia.labs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * Unit tests of {@link ConstantNumberService}.
 */
class ConstantNumberServiceTest {
    private final Number expectedResult = 42L;

    private NumberService numberService;

    @BeforeEach
    void setUp() {
        this.numberService = new ConstantNumberService(expectedResult);
    }

    @Test
    void getNumber() {
        var actualResult = numberService.getNumber();

        assertEquals("Number doesn't match", expectedResult, actualResult);
    }
}
