package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    Bank bank;


    @BeforeEach
    public void setup() {
        bank = new Bank();
    }

    @Test
    public void bank_is_created_with_no_accounts() {
        Bank bank = new Bank();
        double actual = bank.getAccountCount();

        assertEquals(0, actual);
    }

    @Test
    public void when_account_is_added_bank_has_one_account() {
        bank.addAccount(new Checking(87654321, 1.2));
        double actual = bank.getAccountCount();

        assertEquals(1, actual);
    }

    @Test
    public void adding_two_accounts_works_correctly() {
        bank.addAccount(new Checking(87654321, 1));
        bank.addAccount(new CD(01234567, 4, 1000));
        int actual = bank.getAccountCount();

        assertEquals(2, actual);
    }

    @Test
    public void retrieve_correct_account_by_id() {
        bank.addAccount(new Checking(87654321, 1));
        bank.addAccount(new CD(01234567, 4, 1000));
        double actual = bank.getAccount(01234567).getBalance();

        assertEquals(1000, actual);
    }

    @Test
    public void depositing_money_with_id_works_correctly() {
        bank.addAccount(new Checking(87654321, 1));
        bank.addAccount(new CD(01234567, 4, 1000));
        bank.getAccount(87654321).deposit(120.3);
        double actual = bank.getAccount(87654321).getBalance();

        assertEquals(120.3, actual);
    }

    @Test
    public void withdrawing_money_with_id_works_correctly() {
        bank.addAccount(new Checking(87654321, 1));
        bank.addAccount(new CD(01234567, 4, 1000));
        bank.getAccount(01234567).withdraw(55.4);
        double actual = bank.getAccount(01234567).getBalance();

        assertEquals(944.6, actual);
    }

    @Test
    public void depositing_money_twice_with_id_works_correctly() {
        bank.addAccount(new Checking(87654321, 1));
        bank.addAccount(new CD(01234567, 4, 1000));
        bank.getAccount(87654321).deposit(108.2);
        bank.getAccount(87654321).deposit(50);
        double actual = bank.getAccount(87654321).getBalance();

        assertEquals(158.2, actual);
    }

    @Test
    public void withdrawing_money_twice_with_id_works_correctly() {
        bank.addAccount(new Checking(87654321, 1));
        bank.addAccount(new CD(01234567, 4, 1000));
        bank.getAccount(01234567).withdraw(55.4);
        bank.getAccount(01234567).withdraw(60);
        double actual = bank.getAccount(01234567).getBalance();

        assertEquals(884.6, actual);
    }

    @Test
    public void getting_none_existing_account_works_correctly() {
        Account actual = bank.getAccount(12345678);

        assertEquals(null, actual);
    }

    @Test
    public void checkAccountType_works_correctly_for_checking() {
        bank.addAccount(new Checking(12345678, 1));
        boolean actual = bank.checkAccountType(bank.getAccount(12345678), "checking");

        assertTrue(actual);
    }

    @Test
    public void checkAccountType_works_correctly_for_savings() {
        bank.addAccount(new Savings(12345678, 1));
        boolean actual = bank.checkAccountType(bank.getAccount(12345678), "savings");

        assertTrue(actual);
    }

    @Test
    public void checkAccountType_works_correctly_for_cd() {
        bank.addAccount(new CD(12345678, 1, 1000));
        boolean actual = bank.checkAccountType(bank.getAccount(12345678), "cd");

        assertTrue(actual);
    }

    @Test
    public void checkAccountType_works_correctly_for_cd_when_checking_for_something_else() {
        bank.addAccount(new CD(12345678, 1, 1000));
        boolean actual = bank.checkAccountType(bank.getAccount(12345678), "checking");

        assertFalse(actual);
    }

    @Test
    public void checkAccountType_works_correctly_for_non_existing_account() {
        boolean actual = bank.checkAccountType(bank.getAccount(12345678), "");

        assertFalse(actual);
    }

    @Test
    public void transfer_works_correctly_for_valid_input() {
        bank.addAccount(new Checking(12345678, 3));
        bank.addAccount(new Checking(12345679, 6));
        bank.getAccount(12345678).deposit(500);
        bank.transfer(12345678, 12345679, 400);
        double actual1 = bank.getAccount(12345678).getBalance();
        double actual2 = bank.getAccount(12345679).getBalance();

        assertEquals(100, actual1);
        assertEquals(400, actual2);
    }

    @Test
    public void transferring_more_than_balance_works_correctly() {
        bank.addAccount(new Checking(12345678, 3));
        bank.addAccount(new Checking(12345679, 6));
        bank.getAccount(12345678).deposit(100);
        bank.transfer(12345678, 12345679, 150);
        double actual1 = bank.getAccount(12345678).getBalance();
        double actual2 = bank.getAccount(12345679).getBalance();

        assertEquals(0, actual1);
        assertEquals(100, actual2);
    }

    @Test
    public void transferring_exact_amount_as_balance_works_correctly() {
        bank.addAccount(new Checking(12345678, 3));
        bank.addAccount(new Checking(12345679, 6));
        bank.getAccount(12345678).deposit(100);
        bank.transfer(12345678, 12345679, 100);
        double actual1 = bank.getAccount(12345678).getBalance();
        double actual2 = bank.getAccount(12345679).getBalance();

        assertEquals(0, actual1);
        assertEquals(100, actual2);
    }

    @Test
    public void transferAmountLessThanBalance_works_correctly_when_transfer_amount_is_equal_to_balance() {
        bank.addAccount(new Checking(12345678, 3));
        bank.getAccount(12345678).deposit(300);
        boolean actual = bank.transferAmountLessThanBalance(300, bank.getAccount(12345678));

        assertTrue(actual);
    }

    @Test
    public void transferAmountLessThanBalance_works_correctly_when_transfer_amount_is_less_than_balance() {
        bank.addAccount(new Checking(12345678, 3));
        bank.getAccount(12345678).deposit(300);
        boolean actual = bank.transferAmountLessThanBalance(200, bank.getAccount(12345678));

        assertTrue(actual);
    }

    @Test
    public void transferAmountLessThanBalance_works_correctly_when_transfer_amount_is_more_than_balance() {
        bank.addAccount(new Checking(12345678, 3));
        bank.getAccount(12345678).deposit(300);
        boolean actual = bank.transferAmountLessThanBalance(400, bank.getAccount(12345678));

        assertFalse(actual);
    }

    @Test
    public void passTimeForExistingAccounts_works_as_intended() {
        bank.addAccount(new Checking(12345678, 3));
        bank.addAccount(new Savings(87654321, 3));
        bank.addAccount(new CD(12345679, 3, 2000));
        bank.passTimeForExistingAccounts(3);
        int[] actual = {bank.getAccount(12345678).getTimePassed(), bank.getAccount(87654321).getTimePassed(), bank.getAccount(12345679).getTimePassed()};

        assertEquals(3, actual[0]);
        assertEquals(3, actual[1]);
        assertEquals(3, actual[2]);
    }

    @Test
    public void remove_account_which_have_zero_balance() {
        bank.addAccount(new Checking(12345678, 3));
        bank.addAccount(new Savings(87654321, 3));
        bank.addAccount(new CD(12345679, 3, 2000));
        bank.closeAccountsWithZeroBalance();
        int actual = bank.getAccountCount();

        assertEquals(1, actual);
    }

    @Test
    public void deduct_minimum_balance_fee() {
        bank.addAccount(new Checking(12345678, 3));
        bank.getAccount(12345678).deposit(50);
        bank.minimumBalancePayment();
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(25, actual);
    }

    @Test
    public void deduct_minimum_balance_fee_doesnt_deduct_when_balance_is_100() {
        bank.addAccount(new Checking(12345678, 3));
        bank.getAccount(12345678).deposit(100);
        bank.minimumBalancePayment();
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(100, actual);
    }

    @Test
    public void accrueApr_works_correctly_for_checking() {
        bank.addAccount(new Checking(12345678, 0.6));
        bank.getAccount(12345678).deposit(5000);
        bank.accrueApr();
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(5002.5, actual);

    }

    @Test
    public void accrueApr_works_correctly_for_cd() {
        bank.addAccount(new CD(12345678, 2.1, 2000));
        bank.accrueApr();
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(2014.0367928937578, actual);

    }

    @Test
    public void withdraw_is_not_available_for_cd_until_12_months_pass() {
        bank.addAccount(new CD(12345678, 2.1, 2000));
        boolean actual = bank.getAccount(12345678).withdrawAvailable();

        assertFalse(actual);
    }

    @Test
    public void withdraw_is_available_for_cd_when_12_months_pass() {
        bank.addAccount(new CD(12345678, 2.1, 2000));
        bank.passTimeForExistingAccounts(12);
        boolean actual = bank.getAccount(12345678).withdrawAvailable();

        assertTrue(actual);
    }

    @Test
    public void withdraw_is_not_available_for_cd_more_than_once_when_12_months_pass() {
        bank.addAccount(new CD(12345678, 2.1, 2000));
        bank.passTimeForExistingAccounts(12);
        bank.getAccount(12345678).withdraw(2000);
        boolean actual = bank.getAccount(12345678).withdrawAvailable();

        assertFalse(actual);
    }

    @Test
    public void only_one_withdraw_is_available_for_savings_per_month() {
        bank.addAccount(new Savings(12345678, 2.1));
        bank.getAccount(12345678).withdraw(100);
        boolean actual = bank.getAccount(12345678).withdrawAvailable();

        assertFalse(actual);
    }

    @Test
    public void withdraw_can_be_done_after_1_month_passes() {
        bank.addAccount(new Savings(12345678, 2.1));
        bank.getAccount(12345678).deposit(1000);
        bank.getAccount(12345678).withdraw(100);
        bank.passTimeForExistingAccounts(1);
        boolean actual = bank.getAccount(12345678).withdrawAvailable();

        assertTrue(actual);
    }

    @Test
    public void withdraw_can_be_done_unlimited_times_from_checking_per_month() {
        bank.addAccount(new Checking(12345678, 2.1));
        bank.getAccount(12345678).withdraw(100);
        bank.getAccount(12345678).withdraw(100);
        bank.getAccount(12345678).withdraw(100);
        boolean actual = bank.getAccount(12345678).withdrawAvailable();

        assertTrue(actual);
    }

}


