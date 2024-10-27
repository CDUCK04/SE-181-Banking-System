package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawProcessorTest {
    WithdrawProcessor withdrawProcessor;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        withdrawProcessor = new WithdrawProcessor(bank);
    }

    @Test
    public void withdrawProcessor_correctly_withdraws_from_checking() {
        bank.addAccount(new Checking(12345678, 1));
        bank.getAccount(12345678).deposit(1000);
        String[] command = {"withdraw", "12345678", " 300"};
        withdrawProcessor.processWithdraw(command);
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(700, actual);
    }

    @Test
    public void withdrawProcessor_correctly_withdraws_more_than_balance_from_checking() {
        bank.addAccount(new Checking(12345678, 1));
        bank.getAccount(12345678).deposit(100);
        String[] command = {"withdraw", "12345678", " 300"};
        withdrawProcessor.processWithdraw(command);
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void withdrawProcessor_correctly_withdraws_from_savings() {
        bank.addAccount(new Savings(12345678, 1));
        bank.getAccount(12345678).deposit(1500);
        String[] command = {"withdraw", "12345678", " 300"};
        withdrawProcessor.processWithdraw(command);
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(1200, actual);
    }

    @Test
    public void withdrawProcessor_correctly_withdraws_more_than_balance_from_savings() {
        bank.addAccount(new Savings(12345678, 1));
        bank.getAccount(12345678).deposit(100);
        String[] command = {"withdraw", "12345678", " 300"};
        withdrawProcessor.processWithdraw(command);
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void withdrawProcessor_correctly_withdraws_from_cd() {
        bank.addAccount(new CD(12345678, 1, 4000));
        String[] command = {"withdraw", "12345678", " 4000"};
        withdrawProcessor.processWithdraw(command);
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void withdrawProcessor_correctly_withdraws_more_than_balance_from_cd() {
        bank.addAccount(new CD(12345678, 1, 4000));
        String[] command = {"withdraw", "12345678", " 4200"};
        withdrawProcessor.processWithdraw(command);
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(0, actual);
    }

}
