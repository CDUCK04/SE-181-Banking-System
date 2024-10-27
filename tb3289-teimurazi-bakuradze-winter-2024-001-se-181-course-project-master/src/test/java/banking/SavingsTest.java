package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsTest {

    Account savings;

    @BeforeEach
    public void setup() {
        savings = new Savings(12345678, 2.5);
    }

    @Test
    public void create_savings_account_with_0_balance() {
        double actual = savings.getBalance();

        assertEquals(0, actual);
    }
}
