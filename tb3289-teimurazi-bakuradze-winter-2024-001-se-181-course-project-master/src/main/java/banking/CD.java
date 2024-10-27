package banking;

import java.util.Locale;

public class CD extends Account {
    private int passedTime;
    private boolean withdrawCanBeUsed = true;

    public CD(int id, double apr, double balance) {
        super(id, apr, balance);
    }

    @Override
    public boolean isValidDepositAmount(double depositAmount) {
        return false;
    }

    @Override
    public boolean isValidWithdrawAmount(double withdrawAmount) {
        if (withdrawAmount < getBalance()) {
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
        return type.toLowerCase(Locale.ROOT).equals("cd");
    }

    @Override
    public boolean withdrawAvailable() {
        if (getTimePassed() >= 12) {
            return withdrawCanBeUsed;
        }
        return false;
    }

    @Override
    public void useWithdrawForThisMonth() {
        withdrawCanBeUsed = false;
    }

    @Override
    public String getAccountType() {
        return "Cd";
    }

}
