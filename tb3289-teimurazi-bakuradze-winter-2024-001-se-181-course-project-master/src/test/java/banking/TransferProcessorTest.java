package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferProcessorTest {
    TransferProcessor transferProcessor;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        transferProcessor = new TransferProcessor(bank);
    }

    @Test
    public void transfer_processes_correctly_for_correct_input() {
        bank.addAccount(new Checking(12345678, 6));
        bank.addAccount(new Checking(12345679, 5));
        bank.getAccount(12345678).deposit(1000);
        String[] command = {"transfer", "12345678", "12345679", "300"};
        transferProcessor.processTransfer(command);
        double actual1 = bank.getAccount(12345678).getBalance();
        double actual2 = bank.getAccount(12345679).getBalance();

        assertEquals(700, actual1);
        assertEquals(300, actual2);
    }

    @Test
    public void transfer_processes_correctly_when_transfer_is_more_than_balance() {
        bank.addAccount(new Checking(12345678, 6));
        bank.addAccount(new Checking(12345679, 5));
        bank.getAccount(12345678).deposit(100);
        String[] command = {"transfer", "12345678", "12345679", "300"};
        transferProcessor.processTransfer(command);
        double actual1 = bank.getAccount(12345678).getBalance();
        double actual2 = bank.getAccount(12345679).getBalance();

        assertEquals(0, actual1);
        assertEquals(100, actual2);
    }
}
