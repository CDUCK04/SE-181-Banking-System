package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateProcessorTest {
    CreateProcessor createProcessor;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        createProcessor = new CreateProcessor(bank);
    }

    @Test
    void createProcessor_works_correctly_when_creating_checking_account_with_correct_id() {
        String[] command = {"create", "checking", "12345678", "1.0"};
        createProcessor.processCreate(command);
        int actual = bank.getAccount(12345678).getId();

        assertEquals(12345678, actual);
    }

    @Test
    void createProcessor_works_correctly_when_creating_checking_account_with_correct_apr() {
        String[] command = {"create", "checking", "12345678", "3.0"};
        createProcessor.processCreate(command);
        double actual = bank.getAccount(12345678).getApr();

        assertEquals(3, actual);
    }

    @Test
    void createProcessor_works_correctly_when_creating_savings_account_with_correct_id() {
        String[] command = {"create", "savings", "87654321", "4.3"};
        createProcessor.processCreate(command);
        int actual = bank.getAccount(87654321).getId();

        assertEquals(87654321, actual);
    }

    @Test
    void createProcessor_works_correctly_when_creating_savings_account_with_correct_apr() {
        String[] command = {"create", "savings", "12345678", "5.2"};
        createProcessor.processCreate(command);
        double actual = bank.getAccount(12345678).getApr();

        assertEquals(5.2, actual);
    }

    @Test
    void createProcessor_works_correctly_when_creating_cd_account_with_correct_id() {
        String[] command = {"create", "cd", "12345678", "1.0", "1500"};
        createProcessor.processCreate(command);
        int actual = bank.getAccount(12345678).getId();

        assertEquals(12345678, actual);
    }

    @Test
    void createProcessor_works_correctly_when_creating_cd_account_with_correct_apr() {
        String[] command = {"create", "cd", "12345678", "2.7", "1500"};
        createProcessor.processCreate(command);
        double actual = bank.getAccount(12345678).getApr();

        assertEquals(2.7, actual);
    }

    @Test
    void createProcessor_works_correctly_when_creating_cd_account_with_correct_balance() {
        String[] command = {"create", "cd", "12345678", "3.0", "2500"};
        createProcessor.processCreate(command);
        double actual = bank.getAccount(12345678).getBalance();

        assertEquals(2500, actual);
    }
}
