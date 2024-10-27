package banking;


public abstract class Account {
    private double balance;
    private double apr;
    private int id;


    Account(int id, double apr, double balance) {
        this.balance = balance;
        this.apr = apr;
        this.id = id;
    }

    public abstract boolean isValidDepositAmount(double depositAmount);

    public double getBalance() {
        return balance;
    }

    public double getApr() {
        return apr;
    }

    public void deposit(double depositAmount) {
        balance += depositAmount;
    }

    public void withdraw(double withdrawAmount) {
        balance -= withdrawAmount;
        if (balanceIsNegative(balance)) {
            balance = 0;
        }
        useWithdrawForThisMonth();

    }

    public boolean balanceIsNegative(double balance) {
        if (balance < 0) {
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public abstract boolean isValidWithdrawAmount(double withdrawAmount);

    public abstract void passTime(int timeToPass);

    public abstract int getTimePassed();

    public abstract boolean checkType(String type);

    public abstract boolean withdrawAvailable();

    public abstract void useWithdrawForThisMonth();

    public abstract String getAccountType();
}
