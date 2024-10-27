package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositProcessorTest {
    DepositProcessor depositProcessor;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        depositProcessor = new DepositProcessor(bank);
    }

    @Test
    void depositProcessor_deposits_to_checking_correctly() {
        bank.addAccount(new Checking(12345678, 1));
        String[] command = {"deposit", "12345678", "150"};
        depositProcessor.processDeposit(command);
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(150, actual);
    }

    @Test
    void depositProcessor_deposits_to_savings_correctly() {
        bank.addAccount(new Savings(12345678, 1));
        String[] command = {"deposit", "12345678", "150"};
        depositProcessor.processDeposit(command);
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(150, actual);
    }


}
