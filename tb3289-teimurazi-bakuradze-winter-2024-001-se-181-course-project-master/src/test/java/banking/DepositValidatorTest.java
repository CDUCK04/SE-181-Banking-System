package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositValidatorTest {
    Bank bank;
    DepositValidator depositValidator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        depositValidator = new DepositValidator(bank);
    }


    @Test
    void not_able_to_deposit_negative() {
        bank.addAccount(new Checking(12345678, 1));
        boolean actual = depositValidator.depositAmountValidation(12345678, -500);

        assertFalse(actual);
    }

    @Test
    void not_able_to_deposit_more_than_specified_amount_to_checking() {
        bank.addAccount(new Checking(12345678, 1));
        boolean actual = depositValidator.depositAmountValidation(12345678, 1500);

        assertFalse(actual);
    }

    @Test
    void depositValidation_works_correctly_for_checking() {
        bank.addAccount(new Savings(12345678, 1));
        boolean actual = depositValidator.depositAmountValidation(12345678, 500);

        assertTrue(actual);
    }

    @Test
    void not_able_to_deposit_more_than_specified_amount_to_savings() {
        bank.addAccount(new Savings(12345678, 1));
        boolean actual = depositValidator.depositAmountValidation(12345678, 2600);

        assertFalse(actual);
    }

    @Test
    void not_able_to_deposit_negative_amount_to_savings() {
        bank.addAccount(new Savings(12345678, 1));
        boolean actual = depositValidator.depositAmountValidation(12345678, -600);

        assertFalse(actual);
    }

    @Test
    void depositValidation_works_correctly_for_savings() {
        bank.addAccount(new Savings(12345678, 1));
        boolean actual = depositValidator.depositAmountValidation(12345678, 500);

        assertTrue(actual);
    }

    @Test
    void depositValidation_works_correctly_for_savings_when_deposit_amount_is_0() {
        bank.addAccount(new Savings(12345678, 1));
        boolean actual = depositValidator.depositAmountValidation(12345678, 0);

        assertTrue(actual);
    }

    @Test
    void depositValidation_works_correctly_for_savings_when_deposit_amount_is_2500() {
        bank.addAccount(new Savings(12345678, 1));
        boolean actual = depositValidator.depositAmountValidation(12345678, 2500);

        assertTrue(actual);
    }


}
