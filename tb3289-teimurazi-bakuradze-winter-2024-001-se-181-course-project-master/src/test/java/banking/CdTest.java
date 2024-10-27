package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CdTest {

    Account cd;

    @BeforeEach
    public void setup() {
        cd = new CD(01234567, 0.5, 120.5);
    }

    @Test
    public void create_cd_account_with_specific_balance() {
        double actual = cd.getBalance();

        assertEquals(120.5, actual);
    }

    @Test
    public void deposit_specified_amount_to_cd() {
        cd.deposit(100.12);
        double actual = cd.getBalance();

        assertEquals(220.62, actual);
    }

}
