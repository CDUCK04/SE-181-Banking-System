package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeValidatorTest {
    PassTimeValidator passTimeValidator;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        passTimeValidator = new PassTimeValidator(bank);
    }

    @Test
    public void passTimeAmount_cant_be_less_than_1() {
        boolean actual = passTimeValidator.validatePassTimeAmount(0);

        assertFalse(actual);
    }

    @Test
    public void passTimeAmount_cant_be_more_than_60() {
        boolean actual = passTimeValidator.validatePassTimeAmount(61);

        assertFalse(actual);
    }

    @Test
    public void passTimeAmount_can_be_more_than_45() {
        boolean actual = passTimeValidator.validatePassTimeAmount(45);

        assertTrue(actual);
    }

}
