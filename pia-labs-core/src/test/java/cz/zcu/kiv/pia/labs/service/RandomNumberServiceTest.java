package cz.zcu.kiv.pia.labs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.random.RandomGenerator;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * Unit tests of {@link RandomNumberService}.
 */
@ExtendWith(MockitoExtension.class)
class RandomNumberServiceTest {
    @Mock
    private RandomGenerator randomGenerator;

    private NumberService numberService;

    @BeforeEach
    void setUp() {
        this.numberService = new RandomNumberService(randomGenerator);
    }

    @Test
    void getNumber() {
        var expectedResult = 42L;
        when(randomGenerator.nextLong()).thenReturn(expectedResult);

        var actualResult = numberService.getNumber();

        assertEquals("Number doesn't match", expectedResult, actualResult);
    }
}
