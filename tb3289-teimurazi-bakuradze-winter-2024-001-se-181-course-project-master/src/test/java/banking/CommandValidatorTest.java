package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {


    CommandValidator commandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
    }

    @Test
    void correct_checking_creation_validation() {
        boolean actual = commandValidator.validate("Create checking 12345678 4.7");

        assertTrue(actual);
    }

    @Test
    void correct_savings_creation_validation() {
        boolean actual = commandValidator.validate("Create savings 12345678 4.7");

        assertTrue(actual);
    }

    @Test
    void doesnt_allow_to_create_for_inappropriate_apr() {
        boolean actual = commandValidator.validate("Create checking 12345678 12");

        assertFalse(actual);
    }

    @Test
    void checking_cant_be_created_with_starting_balance() {
        boolean actual = commandValidator.validate("Create checking 12345678 5 1000");

        assertFalse(actual);
    }

    @Test
    void checking_cant_be_created_with_inappropriate_id() {
        boolean actual = commandValidator.validate("Create checking 123 4");

        assertFalse(actual);
    }

    @Test
    void account_cant_be_created_with_wrong_type() {
        boolean actual = commandValidator.validate("Create check 12345678 5");

        assertFalse(actual);
    }

    @Test
    void validation_works_correctly_with_no_input() {
        boolean actual = commandValidator.validate("");

        assertFalse(actual);
    }

    @Test
    void account_cant_be_created_with_insufficient_information() {
        boolean actual = commandValidator.validate("Create checking");

        assertFalse(actual);
    }

    @Test
    void cd_can_be_created_with_correct_values() {
        boolean actual = commandValidator.validate("Create Cd 12345678 3 1500");

        assertTrue(actual);
    }

    @Test
    void cd_cant_be_created_without_specific_balance() {
        boolean actual = commandValidator.validate("Create Cd 12345678 3");

        assertFalse(actual);
    }

    @Test
    void cd_cant_be_created_with_inappropriate_balance() {
        boolean actual = commandValidator.validate("Create Cd 12345678 3 10000.01");

        assertFalse(actual);
    }

    @Test
    void cd_cant_be_created_with_negative_balance() {
        boolean actual = commandValidator.validate("Create Cd 12345678 3 -500");

        assertFalse(actual);
    }

    @Test
    void account_cant_be_created_with_create_missing() {
        boolean actual = commandValidator.validate("checking 12345678 5");

        assertFalse(actual);
    }

    @Test
    void account_cant_be_created_with_a_typo_in_create_missing() {
        boolean actual = commandValidator.validate("cre checking 12345678 5");

        assertFalse(actual);
    }

    @Test
    void account_cant_be_created_with_missing_account_type() {
        boolean actual = commandValidator.validate("create 12345678 5");

        assertFalse(actual);
    }

    @Test
    void account_cant_be_created_with_missing_id() {
        boolean actual = commandValidator.validate("create checking 5");

        assertFalse(actual);
    }

    @Test
    void account_cant_be_created_with_missing_apr() {
        boolean actual = commandValidator.validate("create checking 12345678");

        assertFalse(actual);
    }

    @Test
    void validation_is_case_insensitive() {
        boolean actual = commandValidator.validate("cReAte cHECkinG 12345678 4");

        assertTrue(actual);
    }

    @Test
    void account_cant_be_created_with_duplicate_id() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("create checking 12345678 5");

        assertFalse(actual);
    }

    @Test
    void account_cant_be_created_with_too_many_command_part() {
        boolean actual = commandValidator.validate("create checking 12345678 3 12345678");

        assertFalse(actual);
    }

    @Test
    void cant_deposit_more_than_1000_to_checking() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("deposit 12345678 3000");

        assertFalse(actual);
    }

    @Test
    void cant_deposit_more_than_2500_to_savings() {
        bank.addAccount(new Savings(12345678, 4));
        boolean actual = commandValidator.validate("deposit 12345678 3000");

        assertFalse(actual);
    }

    @Test
    void cant_deposit_negative_to_accounts() {
        bank.addAccount(new Savings(12345678, 4));
        boolean actual = commandValidator.validate("deposit 12345678 -100");

        assertFalse(actual);
    }

    @Test
    void cant_deposit_to_cd() {
        bank.addAccount(new CD(12345678, 4, 1500));
        boolean actual = commandValidator.validate("deposit 12345678 500");

        assertFalse(actual);
    }

    @Test
    void depositing_to_account_works_correctly() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("deposit 12345678 500");

        assertTrue(actual);
    }

    @Test
    void depositing_zero_works() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("deposit 12345678 0");

        assertTrue(actual);
    }

    @Test
    void depositing_maximum_amount_works() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("deposit 12345678 1000");

        assertTrue(actual);
    }

    @Test
    void cant_deposit_without_deposit_amount() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("deposit 12345678 ");

        assertFalse(actual);
    }

    @Test
    void cant_deposit_without_account_id() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("deposit 500");

        assertFalse(actual);
    }

    @Test
    void deposit_doesnt_work_with_no_input() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("");

        assertFalse(actual);
    }

    @Test
    void deposit_doesnt_work_with_a_typo_in_deposit() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("dep 12345678 500");

        assertFalse(actual);
    }

    @Test
    void deposit_doesnt_work_with_a_wrong_id() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("deposit 12345679 500");

        assertFalse(actual);
    }

    @Test
    void deposit_doesnt_work_without_a_full_id() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("deposit 123 500");

        assertFalse(actual);
    }

    @Test
    void deposit_doesnt_work_with_too_many_input_command_parts() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("deposit 12345678 500 12345678");

        assertFalse(actual);
    }

    @Test
    void withdrawing_more_than_400_from_checking_doesnt_work() {
        bank.addAccount(new Checking(12345678, 3));
        boolean actual = commandValidator.validate("withdraw 12345678 500");

        assertFalse(actual);
    }

    @Test
    void withdrawing_exactly_400_from_checking_works() {
        bank.addAccount(new Checking(12345678, 3));
        boolean actual = commandValidator.validate("withdraw 12345678 400");

        assertTrue(actual);
    }

    @Test
    void withdrawing_exactly_0_from_checking_works() {
        bank.addAccount(new Checking(12345678, 3));
        boolean actual = commandValidator.validate("withdraw 12345678 0");

        assertTrue(actual);
    }

    @Test
    void withdrawing_negative_from_checking_doesnt_works() {
        bank.addAccount(new Checking(12345678, 3));
        boolean actual = commandValidator.validate("withdraw 12345678 -2");

        assertFalse(actual);
    }

    @Test
    void withdrawing_more_than_1000_from_savings_doesnt_work() {
        bank.addAccount(new Savings(12345678, 3));
        boolean actual = commandValidator.validate("withdraw 12345678 1100");

        assertFalse(actual);
    }

    @Test
    void withdrawing_exactly_1000_from_savings_works() {
        bank.addAccount(new Savings(12345678, 3));
        boolean actual = commandValidator.validate("withdraw 12345678 1000");

        assertTrue(actual);
    }

    @Test
    void withdrawing_exactly_0_from_savings_works() {
        bank.addAccount(new Savings(12345678, 3));
        boolean actual = commandValidator.validate("withdraw 12345678 0");

        assertTrue(actual);
    }

    @Test
    void withdrawing_negative_from_savings_doesnt_works() {
        bank.addAccount(new Savings(12345678, 3));
        boolean actual = commandValidator.validate("withdraw 12345678 -200");

        assertFalse(actual);
    }

    @Test
    void withdrawing_less_than_balance_from_cd_doesnt_work() {
        bank.addAccount(new CD(12345678, 6, 3500));
        boolean actual = commandValidator.validate("withdraw 12345678 3000");

        assertFalse(actual);
    }

    @Test
    void withdrawing_exact_amount_as_balance_from_cd_works() {
        bank.addAccount(new CD(12345678, 0, 3500));
        bank.passTimeForExistingAccounts(12);
        boolean actual = commandValidator.validate("withdraw 12345678 3500");

        assertTrue(actual);
    }

    @Test
    void withdrawing_more_than_balance_from_cd_doesnt_work() {
        bank.addAccount(new CD(12345678, 0, 3500));
        bank.passTimeForExistingAccounts(12);
        boolean actual = commandValidator.validate("withdraw 12345678 4000");

        assertTrue(actual);
    }

    @Test
    void withdrawing_negative_from_cd_doesnt_works() {
        bank.addAccount(new CD(12345678, 3, 3500));
        boolean actual = commandValidator.validate("withdraw 12345678 -200");

        assertFalse(actual);
    }

    @Test
    void cant_withdraw_without_withdraw_amount() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("withdraw 12345678 ");

        assertFalse(actual);
    }

    @Test
    void cant_withdraw_without_account_id() {
        boolean actual = commandValidator.validate("withdraw 300");

        assertFalse(actual);
    }

    @Test
    void withdraw_doesnt_work_with_no_input() {
        boolean actual = commandValidator.validate("");

        assertFalse(actual);
    }

    @Test
    void withdraw_doesnt_work_with_a_typo_in_withdraw() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("with 12345678 300");

        assertFalse(actual);
    }

    @Test
    void withdraw_doesnt_work_with_a_wrong_id() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("withdraw 12345679 300");

        assertFalse(actual);
    }

    @Test
    void withdraw_doesnt_work_without_a_full_id() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("withdraw 123 300");

        assertFalse(actual);
    }

    @Test
    void withdraw_doesnt_work_with_too_many_input() {
        bank.addAccount(new Checking(12345678, 4));
        boolean actual = commandValidator.validate("withdraw 12345678 300 12345678");

        assertFalse(actual);
    }

    @Test
    public void cant_withdraw_form_cd_until_12_months_passed() {
        bank.addAccount(new CD(12345678, 4, 2000));
        boolean actual = commandValidator.validate("withdraw 12345678 2000");

        assertFalse(actual);
    }

    @Test
    public void withdraw_can_be_done_form_cd_after_12_months_passed() {
        bank.addAccount(new CD(12345678, 0, 2000));
        bank.passTimeForExistingAccounts(12);
        boolean actual = commandValidator.validate("withdraw 12345678 2000");

        assertTrue(actual);
    }

    @Test
    public void no_more_than_one_withdraw_can_be_done_per_month_from_savings() {
        bank.addAccount(new Savings(12345678, 2.1));
        bank.getAccount(12345678).withdraw(100);
        boolean actual = commandValidator.validate("withdraw 12345678 100");

        assertFalse(actual);
    }

    @Test
    public void withdraw_can_be_done_from_savings_after_month_passed() {
        bank.addAccount(new Savings(12345678, 2.1));
        bank.getAccount(12345678).withdraw(100);
        bank.passTimeForExistingAccounts(1);
        boolean actual = commandValidator.validate("withdraw 12345678 100");

        assertTrue(actual);
    }

    @Test
    public void any_amount_of_withdraw_is_available_for_checking_per_month() {
        bank.addAccount(new Checking(12345678, 2.1));
        bank.getAccount(12345678).withdraw(100);
        bank.getAccount(12345678).withdraw(100);
        bank.getAccount(12345678).withdraw(100);
        boolean actual = commandValidator.validate("withdraw 12345678 100");

        assertTrue(actual);
    }

    @Test
    void cant_transfer_without_transfer_amount() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Checking(12345679, 3));
        boolean actual = commandValidator.validate("transfer 12345678 12345679");

        assertFalse(actual);
    }

    @Test
    void transfer_doesnt_work_without_idFrom() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Checking(12345679, 3));
        boolean actual = commandValidator.validate("transfer 12345679 400");

        assertFalse(actual);
    }

    @Test
    void transfer_doesnt_work_without_idTo() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Checking(12345679, 3));
        boolean actual = commandValidator.validate("transfer 12345678 400");

        assertFalse(actual);
    }

    @Test
    void transfer_doesnt_work_with_no_input() {
        boolean actual = commandValidator.validate("");

        assertFalse(actual);
    }

    @Test
    void transfer_doesnt_work_with_typo_in_transfer() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Checking(12345679, 3));
        boolean actual = commandValidator.validate("tran 12345678 12345679 400 ");

        assertFalse(actual);
    }

    @Test
    void cant_transfer_with_too_many_input() {
        bank.addAccount(new CD(12345678, 4, 5000));
        bank.addAccount(new Savings(12345679, 4));
        boolean actual = commandValidator.validate("transfer 12345679 12345678 100 123234");

        assertFalse(actual);
    }

    @Test
    void transfer_doesnt_work_with_wrong_idFrom() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Checking(12345679, 3));
        boolean actual = commandValidator.validate("transfer 12345670 12345679 300");

        assertFalse(actual);
    }

    @Test
    void transfer_doesnt_work_with_wrong_idTo() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Checking(12345679, 3));
        boolean actual = commandValidator.validate("transfer 12345678 12345670 300");

        assertFalse(actual);
    }

    @Test
    void transfer_doesnt_work_without_a_full_idFrom() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Checking(12345679, 4));
        boolean actual = commandValidator.validate("transfer 123456 12345679 100");

        assertFalse(actual);
    }

    @Test
    void transfer_works_correctly_for_correct_input() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Checking(12345679, 4));
        boolean actual = commandValidator.validate("transfer 12345678 12345679 100");

        assertTrue(actual);
    }

    @Test
    void transfer_works_between_two_account_types() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Savings(12345679, 4));
        boolean actual = commandValidator.validate("transfer 12345678 12345679 100");

        assertTrue(actual);
    }

    @Test
    void cant_transfer_form_cd() {
        bank.addAccount(new CD(12345678, 4, 5000));
        bank.addAccount(new Savings(12345679, 4));
        boolean actual = commandValidator.validate("transfer 12345678 12345679 100");

        assertFalse(actual);
    }

    @Test
    void cant_transfer_to_cd() {
        bank.addAccount(new CD(12345678, 4, 5000));
        bank.addAccount(new Savings(12345679, 4));
        boolean actual = commandValidator.validate("transfer 12345679 12345678 100");

        assertFalse(actual);
    }

    @Test
    void cant_transfer_more_than_ones_per_month_from_savings() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Savings(12345679, 4));
        bank.transfer(12345679, 12345678, 120);
        boolean actual = commandValidator.validate("transfer 12345679 12345678 100");

        assertFalse(actual);
    }

    @Test
    void money_can_be_transferred_to_savings_more_than_ones_per_month() {
        bank.addAccount(new Checking(12345678, 4));
        bank.addAccount(new Savings(12345679, 4));
        bank.transfer(12345678, 12345679, 120);
        boolean actual = commandValidator.validate("transfer 12345678 12345679 100");

        assertTrue(actual);
    }

    @Test
    void pass_time_validator_works_correctly_for_correct_input() {
        boolean actual = commandValidator.validate("pass 12");

        assertTrue(actual);
    }

    @Test
    void pass_time_validator_doesnt_allow_passing_0() {
        boolean actual = commandValidator.validate("pass 0");

        assertFalse(actual);
    }

    @Test
    void pass_time_validator_doesnt_allow_passing_negative() {
        boolean actual = commandValidator.validate("pass -5");

        assertFalse(actual);
    }

    @Test
    void pass_time_validator_doesnt_allow_passing_more_than_60() {
        boolean actual = commandValidator.validate("pass 100");

        assertFalse(actual);
    }

    @Test
    void pass_time_validator_doesnt_work_with_no_time_amount() {
        boolean actual = commandValidator.validate("pass ");

        assertFalse(actual);
    }

    @Test
    void pass_time_validator_doesnt_work_with_no_command_pass() {
        boolean actual = commandValidator.validate("12");

        assertFalse(actual);
    }

    @Test
    void pass_time_validator_doesnt_work_with_no_command() {
        boolean actual = commandValidator.validate("");

        assertFalse(actual);
    }

    @Test
    void pass_time_validator_doesnt_work_with_typo_in_pass() {
        boolean actual = commandValidator.validate("passs 12");

        assertFalse(actual);
    }

    @Test
    void pass_time_validator_doesnt_work_with_too_many_command_parts() {
        boolean actual = commandValidator.validate("pass 12 12345678");

        assertFalse(actual);
    }

    @Test
    void pass_time_validator_works_for_exactly_1_month() {
        boolean actual = commandValidator.validate("pass 1");

        assertTrue(actual);
    }

    @Test
    void pass_time_validator_works_for_exactly_60_month() {
        boolean actual = commandValidator.validate("pass 60");

        assertTrue(actual);
    }

}


