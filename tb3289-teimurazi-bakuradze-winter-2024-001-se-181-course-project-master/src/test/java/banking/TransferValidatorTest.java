package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferValidatorTest {
    TransferValidator transferValidator;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        transferValidator = new TransferValidator(bank);
    }

    @Test
    public void transfer_validates_correctly_for_correct_input() {
        bank.addAccount(new Checking(12345678, 5));
        bank.addAccount(new Checking(12345679, 5));
        String[] command = {"transfer", "12345678", "12345679", "300"};
        boolean actual = transferValidator.validateTransfer(command);

        assertTrue(actual);
    }

    @Test
    public void cant_transfer_if_one_of_the_accounts_doesnt_exist() {
        bank.addAccount(new Checking(12345678, 5));
        String[] command = {"transfer", "12345678", "12345679", "300"};
        boolean actual = transferValidator.validateTransfer(command);

        assertFalse(actual);
    }

    @Test
    public void cant_transfer_if_none_of_the_accounts_exist() {
        String[] command = {"transfer", "12345678", "12345679", "300"};
        boolean actual = transferValidator.validateTransfer(command);

        assertFalse(actual);
    }

    @Test
    public void cant_transfer_if_transfer_amount_is_more_than_withdraw_limit() {
        bank.addAccount(new Checking(12345678, 5));
        bank.addAccount(new Checking(12345679, 5));
        String[] command = {"transfer", "12345678", "12345679", "500"};
        boolean actual = transferValidator.validateTransfer(command);

        assertFalse(actual);
    }

    @Test
    public void cant_transfer_if_transfer_amount_is_negative() {
        bank.addAccount(new Checking(12345678, 5));
        bank.addAccount(new Checking(12345679, 5));
        String[] command = {"transfer", "12345678", "12345679", "-500"};
        boolean actual = transferValidator.validateTransfer(command);

        assertFalse(actual);
    }

    @Test
    public void cant_transfer_if_cd_is_account_transferred_from() {
        bank.addAccount(new CD(12345678, 5, 5000));
        bank.addAccount(new Checking(12345679, 5));
        String[] command = {"transfer", "12345678", "12345679", "5000"};
        boolean actual = transferValidator.validateTransfer(command);

        assertFalse(actual);
    }

    @Test
    public void cant_transfer_if_cd_is_account_transferred_to() {
        bank.addAccount(new Checking(12345678, 5));
        bank.addAccount(new CD(12345679, 5, 5000));
        String[] command = {"transfer", "12345678", "12345679", "5000"};
        boolean actual = transferValidator.validateTransfer(command);

        assertFalse(actual);
    }

    @Test
    public void cant_transfer_to_the_same_account() {
        bank.addAccount(new Checking(12345678, 3));
        String[] command = {"transfer", "12345678", "12345678", "100"};
        boolean actual = transferValidator.validateTransfer(command);

        assertFalse(actual);
    }


}
