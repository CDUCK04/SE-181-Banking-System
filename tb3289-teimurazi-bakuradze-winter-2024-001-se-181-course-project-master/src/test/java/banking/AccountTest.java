package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {

    Account checking;
    Account savings;
    Account cd;

    @BeforeEach
    public void setup() {
        checking = new Checking(87654321, 2.5);
        savings = new Savings(12345678, 4);
        cd = new CD(01234567, 5, 1500);

    }

    @Test
    public void create_checking_account_with_specific_apr() {
        double actual = checking.getApr();

        assertEquals(2.5, actual);
    }

    @Test
    public void deposit_specified_amount_to_checking() {
        checking.deposit(100.12);
        double actual = checking.getBalance();

        assertEquals(100.12, actual);
    }

    @Test
    public void deposit_specific_amount_to_checking_than_withdraw() {
        checking.deposit(500);
        checking.withdraw(109.6);
        double actual = checking.getBalance();

        assertEquals(390.4, actual);

    }

    @Test
    public void while_withdrawing_from_checking_it_cant_go_below_zero() {
        checking.deposit(10);
        checking.withdraw(100);
        double actual = checking.getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void depositing_twice_to_checking_works_correctly() {
        checking.deposit(10.5);
        checking.deposit(100);
        double actual = checking.getBalance();

        assertEquals(110.5, actual);
    }

    @Test
    public void withdrawing_twice_from_checking_works_correctly() {
        checking.deposit(400);
        checking.withdraw(120);
        checking.withdraw(2.3);
        double actual = checking.getBalance();

        assertEquals(277.7, actual);
    }

    @Test
    public void correctly_gets_checking_accounts_id() {
        int actual = checking.getId();

        assertEquals(87654321, actual);
    }

    @Test
    public void withdrawing_zero_from_balance_works_correctly() {
        checking.withdraw(0);
        double actual = checking.getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void withdrawing_exactly_balance_amount_goes_to_zero() {
        checking.deposit(10);
        checking.withdraw(10);
        double actual = checking.getBalance();

        assertEquals(0, actual);
    }

    @Test
    public void balanceIsNegative_works_correctly() {
        boolean actual = checking.balanceIsNegative(checking.getBalance());

        assertFalse(actual);
    }

    @Test
    public void isWithdrawAmountValid_works_correctly_for_checking_for_correct_amount() {
        boolean actual = checking.isValidWithdrawAmount(100);

        assertTrue(actual);
    }

    @Test
    public void isWithdrawAmountValid_works_correctly_for_checking_for_invalid_amount() {
        boolean actual = checking.isValidWithdrawAmount(600);

        assertFalse(actual);
    }

    @Test
    public void isWithdrawAmountValid_works_correctly_for_cd_for_correct_amount() {
        boolean actual = cd.isValidWithdrawAmount(1500);

        assertTrue(actual);
    }

    @Test
    public void isWithdrawAmountValid_works_correctly_for_cd_for_invalid_amount() {
        boolean actual = cd.isValidWithdrawAmount(600);

        assertFalse(actual);
    }

    @Test
    public void isWithdrawAmountValid_works_correctly_for_savings_for_correct_amount() {
        boolean actual = savings.isValidWithdrawAmount(100);

        assertTrue(actual);
    }

    @Test
    public void isWithdrawAmountValid_works_correctly_for_savings_for_invalid_amount() {
        boolean actual = savings.isValidWithdrawAmount(1100);

        assertFalse(actual);
    }

    @Test
    public void pass_time_works_correctly_for_checking() {
        checking.passTime(12);
        int actual = checking.getTimePassed();

        assertEquals(12, actual);
    }

    @Test
    public void pass_time_works_correctly_for_savings() {
        savings.passTime(3);
        int actual = savings.getTimePassed();

        assertEquals(3, actual);
    }

    @Test
    public void pass_time_works_correctly_for_cd() {
        cd.passTime(5);
        int actual = cd.getTimePassed();

        assertEquals(5, actual);
    }

    @Test
    void getting_account_type_works_correctly_for_checking() {
        String actual = checking.getAccountType();

        assertEquals("Checking", actual);
    }

    @Test
    void getting_account_type_works_correctly_for_Savings() {
        String actual = savings.getAccountType();

        assertEquals("Savings", actual);
    }

    @Test
    void getting_account_type_works_correctly_for_cd() {
        String actual = cd.getAccountType();

        assertEquals("Cd", actual);
    }

}
