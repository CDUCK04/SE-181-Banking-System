package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawValidatorTest {
    WithdrawValidator withdrawaValidator;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        withdrawaValidator = new WithdrawValidator(bank);

    }

    @Test
    public void withdrawAmountValidation_works_correctly_for_right_value_for_checking_account() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = withdrawaValidator.withdrawAmountValidation(12345678, 400);

        assertTrue(actual);
    }

    @Test
    public void withdrawAmountValidation_works_correctly_for_incorrect_value_for_checking_account() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = withdrawaValidator.withdrawAmountValidation(12345678, 600);

        assertFalse(actual);
    }

    @Test
    public void withdrawAmountValidation_works_correctly_for_right_value_for_savings_account() {
        bank.addAccount(new Savings(12345678, 4));
        boolean actual = withdrawaValidator.withdrawAmountValidation(12345678, 400);

        assertTrue(actual);
    }

    @Test
    public void withdrawAmountValidation_works_correctly_for_incorrect_value_for_savings_account() {
        bank.addAccount(new Savings(12345678, 4));
        boolean actual = withdrawaValidator.withdrawAmountValidation(12345678, 1100);

        assertFalse(actual);
    }

    @Test
    public void withdrawAmountValidation_works_correctly_for_right_value_for_cd_account() {
        bank.addAccount(new CD(12345678, 4, 2000));
        boolean actual = withdrawaValidator.withdrawAmountValidation(12345678, 2000);

        assertTrue(actual);
    }

    @Test
    public void withdrawAmountValidation_works_correctly_for_incorrect_value_for_cd_account() {
        bank.addAccount(new CD(12345678, 4, 2000));
        boolean actual = withdrawaValidator.withdrawAmountValidation(12345678, 500);

        assertFalse(actual);
    }


}
