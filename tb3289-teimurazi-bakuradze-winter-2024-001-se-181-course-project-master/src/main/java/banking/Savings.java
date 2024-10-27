package banking;

import java.util.Locale;

public class Savings extends Account {

    public static final double SAVINGS_DEFAULT_BALANCE = 0;
    public static final double SAVINGS_WITHDRAW_MINIMUM_AMOUNT = 0;
    public static final double SAVINGS_WITHDRAW_MAXIMUM_AMOUNT = 1000;
    public static final double SAVINGS_DEPOSIT_MINIMUM_AMOUNT = 0;
    public static final double SAVINGS_DEPOSIT_MAXIMUM_AMOUNT = 2500;
    private int passedTime;
    private boolean withdrawUsed = false;

    Savings(int id, double apr) {
        super(id, apr, SAVINGS_DEFAULT_BALANCE);
    }

    @Override
    public boolean isValidDepositAmount(double depositAmount) {
        if (depositAmount < SAVINGS_DEPOSIT_MINIMUM_AMOUNT || depositAmount > SAVINGS_DEPOSIT_MAXIMUM_AMOUNT) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidWithdrawAmount(double withdrawAmount) {
        if (withdrawAmount > SAVINGS_WITHDRAW_MAXIMUM_AMOUNT || withdrawAmount < SAVINGS_WITHDRAW_MINIMUM_AMOUNT) {
            return false;
        }
        return true;
    }

    @Override
    public void passTime(int timeToPass) {
        this.passedTime += timeToPass;
        this.withdrawUsed = false;
    }

    @Override
    public int getTimePassed() {
        return passedTime;
    }

    @Override
    public boolean checkType(String type) {
        return type.toLowerCase(Locale.ROOT).equals("savings");
    }

    @Override
    public boolean withdrawAvailable() {
        return !withdrawUsed;
    }

    @Override
    public void useWithdrawForThisMonth() {
        this.withdrawUsed = true;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }


}
