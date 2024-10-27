package banking;

import java.util.Locale;

public class Checking extends Account {
    public static final double CHECKING_DEFAULT_BALANCE = 0;
    public static final double CHECKING_WITHDRAW_MINIMUM_AMOUNT = 0;
    public static final double CHECKING_WITHDRAW_MAXIMUM_AMOUNT = 400;
    public static final double CHECKING_DEPOSIT_MINIMUM_AMOUNT = 0;
    public static final double CHECKING_DEPOSIT_MAXIMUM_AMOUNT = 1000;
    private int passedTime;
    private boolean withdrawUsed = false;

    Checking(int id, double apr) {
        super(id, apr, CHECKING_DEFAULT_BALANCE);
    }

    @Override
    public boolean isValidDepositAmount(double depositAmount) {
        if (depositAmount < CHECKING_DEPOSIT_MINIMUM_AMOUNT || depositAmount > CHECKING_DEPOSIT_MAXIMUM_AMOUNT) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidWithdrawAmount(double withdrawAmount) {
        if (withdrawAmount > CHECKING_WITHDRAW_MAXIMUM_AMOUNT || withdrawAmount < CHECKING_WITHDRAW_MINIMUM_AMOUNT) {
            return false;
        }
        return true;
    }

    @Override
    public void passTime(int timeToPass) {
        this.passedTime += timeToPass;
    }

    @Override
    public int getTimePassed() {

        return passedTime;
    }

    @Override
    public boolean checkType(String type) {
        return type.toLowerCase(Locale.ROOT).equals("checking");
    }

    @Override
    public boolean withdrawAvailable() {
        return true;
    }

    @Override
    public void useWithdrawForThisMonth() {
        withdrawUsed = false;
    }

    @Override
    public String getAccountType() {
        return "Checking";
    }


}
