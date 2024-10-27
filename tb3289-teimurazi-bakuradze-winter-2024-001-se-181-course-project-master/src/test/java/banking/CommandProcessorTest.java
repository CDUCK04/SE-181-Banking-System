package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandProcessorTest {
    Bank bank;
    CommandProcessor commandProcessor;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    void create_checking_account_with_correct_id() {
        commandProcessor.processCommand("create checking 12345678 1.0");
        int actual = bank.getAccount(12345678).getId();

        assertEquals(12345678, actual);
    }

    @Test
    void create_checking_account_with_correct_apr() {
        commandProcessor.processCommand("create checking 12345678 1.0");
        double actual = bank.getAccount(12345678).getApr();

        assertEquals(1, actual);
    }

    @Test
    void create_savings_account_with_correct_id() {
        commandProcessor.processCommand("create savings 87654321 1.0");
        int actual = bank.getAccount(87654321).getId();

        assertEquals(87654321, actual);
    }

    @Test
    void create_savings_account_with_correct_apr() {
        commandProcessor.processCommand("create savings 12345678 1.0");
        double actual = bank.getAccount(12345678).getApr();

        assertEquals(1, actual);
    }

    @Test
    void create_cd_account_with_correct_id() {
        commandProcessor.processCommand("create cd 12345678 1.0 1500");
        int actual = bank.getAccount(12345678).getId();

        assertEquals(12345678, actual);
    }

    @Test
    void create_cd_account_with_correct_apr() {
        commandProcessor.processCommand("create savings 12345678 1.0 1500");
        double actual = bank.getAccount(12345678).getApr();

        assertEquals(1, actual);
    }

    @Test
    void create_cd_account_with_correct_balance() {
        commandProcessor.processCommand("create cd 12345678 1.0 1500");
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(1500, actual);
    }

    @Test
    void deposit_to_checking_works_correctly() {
        bank.addAccount(new Checking(12345678, 1));
        commandProcessor.processCommand("deposit 12345678 150");
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(150, actual);
    }

    @Test
    void deposit_to_savings_works_correctly() {
        bank.addAccount(new Savings(12345678, 1));
        commandProcessor.processCommand("deposit 12345678 150");
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(150, actual);
    }

    @Test
    void deposit_to_cd_works_correctly() {
        bank.addAccount(new CD(12345678, 1, 1500));
        commandProcessor.processCommand("deposit 12345678 150");
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(1650, actual);
    }

    @Test
    public void withdrawing_works_correctly_for_checking() {
        bank.addAccount(new Checking(12345678, 1));
        bank.getAccount(12345678).deposit(1000);
        commandProcessor.processCommand("withdraw 12345678 300");
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(700, actual);
    }

    @Test
    public void withdrawing_more_than_balance_works_correctly_for_checking() {
        bank.addAccount(new Checking(12345678, 1));
        bank.getAccount(12345678).deposit(100);
        commandProcessor.processCommand("withdraw 12345678 300");
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void withdrawing_works_correctly_for_savings() {
        bank.addAccount(new Savings(12345678, 1));
        bank.getAccount(12345678).deposit(1500);
        commandProcessor.processCommand("withdraw 12345678 300");
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(1200, actual);
    }

    @Test
    public void withdrawing_more_than_balance_works_correctly_for_savings() {
        bank.addAccount(new Savings(12345678, 1));
        bank.getAccount(12345678).deposit(100);
        commandProcessor.processCommand("withdraw 12345678 300");
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void withdrawing_works_correctly_for_cd() {
        bank.addAccount(new CD(12345678, 1, 4000));
        commandProcessor.processCommand("withdraw 12345678 4000");
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void withdrawing_more_than_balance_works_correctly_for_cd() {
        bank.addAccount(new CD(12345678, 1, 4000));
        commandProcessor.processCommand("withdraw 12345678 4200");
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void transfer_works_correctly_for_right_input() {
        bank.addAccount(new Checking(12345678, 6));
        bank.addAccount(new Checking(12345679, 5));
        bank.getAccount(12345678).deposit(1000);
        commandProcessor.processCommand("transfer 12345678 12345679 300");
        double actual1 = bank.getAccount(12345678).getBalance();
        double actual2 = bank.getAccount(12345679).getBalance();

        assertEquals(700, actual1);
        assertEquals(300, actual2);
    }

    @Test
    public void transfer_works_correctly_when_transfer_is_more_than_balance() {
        bank.addAccount(new Checking(12345678, 6));
        bank.addAccount(new Checking(12345679, 5));
        bank.getAccount(12345678).deposit(100);
        commandProcessor.processCommand("transfer 12345678 12345679 300");
        double actual1 = bank.getAccount(12345678).getBalance();
        double actual2 = bank.getAccount(12345679).getBalance();

        assertEquals(0, actual1);
        assertEquals(100, actual2);
    }

    @Test
    public void transfer_works_correctly_between_different_account_types() {
        bank.addAccount(new Checking(12345678, 6));
        bank.addAccount(new Savings(12345679, 5));
        bank.getAccount(12345678).deposit(2500);
        commandProcessor.processCommand("transfer 12345678 12345679 300");
        double actual1 = bank.getAccount(12345678).getBalance();
        double actual2 = bank.getAccount(12345679).getBalance();

        assertEquals(2200, actual1);
        assertEquals(300, actual2);
    }

    @Test
    public void pass_time_works_correctly_for_checking() {
        bank.addAccount(new Checking(12345678, 6));
        bank.getAccount(12345678).deposit(100);
        commandProcessor.processCommand("pass 24");
        int actual = bank.getAccount(12345678).getTimePassed();

        assertEquals(24, actual);
    }

    @Test
    public void pass_time_works_correctly_for_savings() {
        bank.addAccount(new Savings(12345678, 6));
        bank.getAccount(12345678).deposit(100);
        commandProcessor.processCommand("pass 20");
        int actual = bank.getAccount(12345678).getTimePassed();

        assertEquals(20, actual);
    }

    @Test
    public void pass_time_works_correctly_for_cd() {
        bank.addAccount(new CD(12345678, 6, 1200));
        commandProcessor.processCommand("pass 15");
        int actual = bank.getAccount(12345678).getTimePassed();

        assertEquals(15, actual);
    }

    @Test
    public void all_0_balance_accounts_get_closed() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Savings(12345679, 4));
        bank.addAccount(new CD(87654321, 4, 1500));
        commandProcessor.processCommand("pass 1");
        int actual = bank.getAccountCount();

        assertEquals(1, actual);
    }

    @Test
    public void all_accounts_pay_minimum_balance_fee_and_0_balance_ones_get_canceled() {
        bank.addAccount(new Checking(12345678, 0));
        bank.addAccount(new Savings(12345679, 0));
        bank.addAccount(new CD(87654321, 4, 1500));
        bank.getAccount(12345678).deposit(75);
        commandProcessor.processCommand("pass 3");
        int actual1 = bank.getAccountCount();
        double actual2 = bank.getAccount(12345678).getBalance();

        assertEquals(2, actual1);
        assertEquals(0, actual2);
    }

    @Test
    public void all_accounts_are_correctly_affected_when_passing_time() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Savings(12345679, 4));
        bank.addAccount(new CD(87654321, 4, 1500));
        bank.getAccount(12345678).deposit(75);
        commandProcessor.processCommand("pass 1");
        int actual1 = bank.getAccountCount();
        double actual2 = bank.getAccount(12345678).getBalance();
        double actual3 = bank.getAccount(87654321).getBalance();

        assertEquals(2, actual1);
        assertEquals(50.166666666666664, actual2);
        assertEquals(1520.1002224074073, actual3);
    }

}
