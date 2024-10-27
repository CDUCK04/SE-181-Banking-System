package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateValidatorTest {
    Bank bank;
    CreateValidator createValidator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createValidator = new CreateValidator(bank);
    }

    @Test
    void apr_cant_be_more_than_ten() {
        boolean actual = createValidator.validateApr(11);

        assertFalse(actual);
    }

    @Test
    void apr_cant_be_less_than_zero() {
        boolean actual = createValidator.validateApr(-1);

        assertFalse(actual);
    }

    @Test
    void apr_can_be_created_for_zero() {
        boolean actual = createValidator.validateApr(0);

        assertTrue(actual);
    }

    @Test
    void apr_can_be_created_for_ten() {
        boolean actual = createValidator.validateApr(10);

        assertTrue(actual);
    }


    @Test
    void apr_validator_works_correctly_for_right_value() {
        boolean actual = createValidator.validateApr(5.2);

        assertTrue(actual);
    }

    @Test
    void id_must_not_be_less_than_eight_digits() {
        boolean actual = createValidator.idValidator("123");

        assertFalse(actual);
    }

    @Test
    void id_must_not_be_more_than_eight_digits() {
        boolean actual = createValidator.idValidator("123456789");

        assertFalse(actual);
    }

    @Test
    void id_validator_works_correctly_for_correct_value() {
        boolean actual = createValidator.idValidator("12345678");

        assertTrue(actual);
    }

    @Test
    void id_validator_doesnt_allow_duplicate_ids() {
        bank.addAccount(new Checking(12345678, 1));
        boolean actual = createValidator.idValidator("12345678");

        assertFalse(actual);
    }

    @Test
    void validateCreate_creates_checking_account() {
        boolean actual = createValidator.validateCreate(new String[]{"create", "checking", "12345678", "5.2"});

        assertTrue(actual);
    }

    @Test
    void validateCreate_creates_savings_account() {
        boolean actual = createValidator.validateCreate(new String[]{"create", "savings", "12345678", "5.2"});

        assertTrue(actual);
    }

    @Test
    void cd_starting_balance_cant_be_less_than_thousand() {
        boolean actual = createValidator.cdStartingBalanceValidation(999.9);

        assertFalse(actual);
    }

    @Test
    void cd_starting_balance_cant_be_more_than_ten_thousand() {
        boolean actual = createValidator.cdStartingBalanceValidation(10000.01);

        assertFalse(actual);
    }

    @Test
    void checkingAndSavingValidation_works_correctly() {
        boolean actual = createValidator.validateCreate(new String[]{"create", "savings", "12345678", "5.2"});

        assertTrue(actual);
    }

    @Test
    void cdValidation_works_correctly() {
        boolean actual = createValidator.validateCreate(new String[]{"create", "cd", "12345678", "5.2", "1500"});

        assertTrue(actual);
    }

    @Test
    void cd_starting_balance_is_exactly_1000() {
        boolean actual = createValidator.cdStartingBalanceValidation(1000);

        assertTrue(actual);
    }

    @Test
    void cd_starting_balance_is_exactly_10000() {
        boolean actual = createValidator.cdStartingBalanceValidation(10000);

        assertTrue(actual);
    }

}
