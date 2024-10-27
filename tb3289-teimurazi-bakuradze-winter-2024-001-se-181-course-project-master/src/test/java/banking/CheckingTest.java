package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingTest {

    Account checking;

    @BeforeEach
    public void setup() {
        checking = new Checking(87654321, 2.5);
    }

    @Test
    public void create_checking_account_with_0_balance() {
        double actual = checking.getBalance();

        assertEquals(0, actual);
    }
}
