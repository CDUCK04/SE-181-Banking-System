package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputTest {
    Output output;
    CommandStorage commandStorage;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        output = new Output(commandStorage, bank);
    }

    @Test
    public void getting_account_status_works_correctly_for_checking() {
        bank.addAccount(new Checking(12345678, 2));
        bank.getAccount(12345678).deposit(700);
        String actual = output.getAccountStatus(12345678);

        assertEquals("Checking 12345678 700.00 2.00", actual);
    }

    @Test
    public void getting_account_status_works_correctly_for_savings() {
        bank.addAccount(new Savings(12345678, 2));
        bank.getAccount(12345678).deposit(700);
        String actual = output.getAccountStatus(12345678);

        assertEquals("Savings 12345678 700.00 2.00", actual);
    }

    @Test
    public void getting_account_status_works_correctly_for_cd() {
        bank.addAccount(new CD(12345678, 2, 5000));
        bank.getAccount(12345678).withdraw(700.99);
        String actual = output.getAccountStatus(12345678);

        assertEquals("Cd 12345678 4299.01 2.00", actual);
    }
}
