package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassTimeProcessorTest {
    PassTimeProcessor passTimeProcessor;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        passTimeProcessor = new PassTimeProcessor(bank);
    }

    @Test
    public void passing_10_months_works_correctly_for_checking() {
        bank.addAccount(new Checking(12345678, 4));
        bank.getAccount(12345678).deposit(100);
        String[] command = {"pass", "10"};
        passTimeProcessor.processPassTime(command);
        int actual = bank.getAccount(12345678).getTimePassed();

        assertEquals(10, actual);

    }

    @Test
    public void passing_10_months_works_correctly_for_savings() {
        bank.addAccount(new Savings(12345678, 4));
        bank.getAccount(12345678).deposit(100);
        String[] command = {"pass", "10"};
        passTimeProcessor.processPassTime(command);
        int actual = bank.getAccount(12345678).getTimePassed();

        assertEquals(10, actual);

    }

    @Test
    public void passing_10_months_works_correctly_for_cd() {
        bank.addAccount(new CD(12345678, 4, 1500));
        String[] command = {"pass", "10"};
        passTimeProcessor.processPassTime(command);
        int actual = bank.getAccount(12345678).getTimePassed();

        assertEquals(10, actual);

    }

    @Test
    public void all_0_balance_accounts_are_closed() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Savings(12345679, 4));
        bank.addAccount(new CD(87654321, 4, 1500));
        String[] command = {"pass", "1"};
        passTimeProcessor.processPassTime(command);
        int actual = bank.getAccountCount();

        assertEquals(1, actual);
    }

    @Test
    public void all_accounts_are_deducted_minimum_balance_fee_and_0_balance_accounts_closed() {
        bank.addAccount(new Checking(12345678, 0));
        bank.addAccount(new Savings(12345679, 0));
        bank.addAccount(new CD(87654321, 4, 1500));
        bank.getAccount(12345678).deposit(75);
        String[] command = {"pass", "3"};
        passTimeProcessor.processPassTime(command);
        int actual1 = bank.getAccountCount();
        double actual2 = bank.getAccount(12345678).getBalance();

        assertEquals(2, actual1);
        assertEquals(0, actual2);
    }

    @Test
    public void all_accounts_are_correctly_affected_by_post_pass_time_actions() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Savings(12345679, 4));
        bank.addAccount(new CD(87654321, 4, 1500));
        bank.getAccount(12345678).deposit(75);
        String[] command = {"pass", "1"};
        passTimeProcessor.processPassTime(command);
        int actual1 = bank.getAccountCount();
        double actual2 = bank.getAccount(12345678).getBalance();
        double actual3 = bank.getAccount(87654321).getBalance();

        assertEquals(2, actual1);
        assertEquals(50.166666666666664, actual2);
        assertEquals(1520.1002224074073, actual3);
    }


}
