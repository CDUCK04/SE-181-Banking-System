package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {
    MasterControl masterControl;
    List<String> input;

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @BeforeEach
    void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        CommandStorage commandStorage = new CommandStorage();
        masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), commandStorage, new Output(commandStorage, bank));
    }

    @Test
    void typo_in_create_command_is_invalid() {
        input.add("creat checking 12345678 1.0");
        List<String> actual = masterControl.start(input);

        assertSingleCommand("creat checking 12345678 1.0", actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid() {
        input.add("depositt 12345678 100");
        List<String> actual = masterControl.start(input);

        assertSingleCommand("depositt 12345678 100", actual);
    }

    @Test
    void two_typo_commands_both_invalid() {
        input.add("creat checking 12345678 1.0");
        input.add("depositt 12345678 100");
        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("creat checking 12345678 1.0", actual.get(0));
        assertEquals("depositt 12345678 100", actual.get(1));
    }

    @Test
    void invalid_to_create_accounts_with_same_ID() {
        input.add("create checking 12345678 1.0");
        input.add("create checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
        assertEquals("create checking 12345678 1.0", actual.get(1));
    }

    @Test
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }

    @Test
    void final_test_1() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(8, actual.size());
        assertEquals("Savings 12345678 1000.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Checking 98765432 0.00 0.01", actual.get(3));
        assertEquals("Deposit 98765432 300", actual.get(4));
        assertEquals("Transfer 98765432 12345678 300", actual.get(5));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(6));
        assertEquals("Deposit 12345678 5000", actual.get(7));
    }

    @Test
    void final_test_2() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("pass 12");
        input.add("Create cd 23456789 1.2 2000");
        input.add("withdraw 23456789 1500");
        List<String> actual = masterControl.start(input);

        assertEquals(6, actual.size());
        assertEquals("Savings 12345678 1006.01 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
        assertEquals("withdraw 23456789 1500", actual.get(5));
    }

    @Test
    public void testCreateAccountSuccessAndFailure() {
        input.add("Create checking 12345678 0.5");
        input.add("Create savings 87654321 1.0");
        input.add("Create cd 11223344 1.5 5000");
        input.add("Create checking 12345678 0.5");
        input.add("Create savings 1234abcd 1.2");
        input.add("Create holiday 87654322 0.7");

        List<String> actual = masterControl.start(input);

        assertEquals(6, actual.size());
        assertEquals("Checking 12345678 0.00 0.50", actual.get(0));
        assertEquals("Savings 87654321 0.00 1.00", actual.get(1));
        assertEquals("Cd 11223344 5000.00 1.50", actual.get(2));
        assertEquals("Create checking 12345678 0.5", actual.get(3));
        assertEquals("Create savings 1234abcd 1.2", actual.get(4));
        assertEquals("Create holiday 87654322 0.7", actual.get(5));
    }

    @Test
    public void testDepositAndWithdrawalLimitsAndRules() {
        input.add("Create checking 12345678 0.5");
        input.add("Create savings 87654321 1.0");
        input.add("Deposit 12345678 500");
        input.add("Deposit 87654321 3000");
        input.add("Withdraw 12345678 500");
        input.add("Withdraw 87654321 600");
        input.add("Deposit 12345678 -100");

        List<String> actual = masterControl.start(input);

        assertEquals(7, actual.size());
        assertEquals("Checking 12345678 500.00 0.50", actual.get(0));
        assertEquals("Deposit 12345678 500", actual.get(1));
        assertEquals("Savings 87654321 0.00 1.00", actual.get(2));
        assertEquals("Withdraw 87654321 600", actual.get(3));
        assertEquals("Deposit 87654321 3000", actual.get(4));
        assertEquals("Withdraw 12345678 500", actual.get(5));
        assertEquals("Deposit 12345678 -100", actual.get(6));

    }

    @Test
    public void testTransferFunds() {
        input.add("Create checking 12345678 0.01");
        input.add("Create savings 87654321 0.6");
        input.add("Create cd 11223344 2.5 3000");
        input.add("Transfer 12345678 87654321 300");
        input.add("Transfer 12345678 11223344 200");
        input.add("Transfer 12345678 12345678 100");
        input.add("Transfer 12345678 87654321 1000");

        List<String> actual = masterControl.start(input);

        assertEquals(8, actual.size());
        assertEquals("Checking 12345678 0.00 0.01", actual.get(0));
        assertEquals("Transfer 12345678 87654321 300", actual.get(1));
        assertEquals("Savings 87654321 0.00 0.60", actual.get(2));
        assertEquals("Transfer 12345678 87654321 300", actual.get(3));
        assertEquals("Cd 11223344 3000.00 2.50", actual.get(4));
        assertEquals("Transfer 12345678 11223344 200", actual.get(5));
        assertEquals("Transfer 12345678 12345678 100", actual.get(6));
        assertEquals("Transfer 12345678 87654321 1000", actual.get(7));
    }

    @Test
    public void testPassingTimeAndAccountBehavior() {
        input.add("Pass 1");
        input.add("Pass 0");
        input.add("Pass 61");
        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Pass 0", actual.get(0));
        assertEquals("Pass 61", actual.get(1));
    }

    @Test
    public void testComprehensiveScenario() {
        input.add("Create checking 12345678 0.01");
        input.add("Deposit 12345678 500");
        input.add("Create savings 87654321 0.6");
        input.add("Transfer 12345678 87654321 200");
        input.add("Withdraw 87654321 300");
        input.add("Create cd 11223344 2.5 3000");
        input.add("Pass 12");
        input.add("Withdraw 11223344 3100");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Checking 12345678 300.03 0.01", actual.get(0));
        assertEquals("Deposit 12345678 500", actual.get(1));
        assertEquals("Transfer 12345678 87654321 200", actual.get(2));
        assertEquals("Cd 11223344 3315.16 2.50", actual.get(3));
        assertEquals("Withdraw 11223344 3100", actual.get(4));


    }
}
