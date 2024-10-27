package banking;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Bank {
    public static final int MINIMUM_BALANCE_FEE = 25;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private Map<Integer, Account> accounts;


    public Bank() {
        accounts = new HashMap<>();
    }

    public int getAccountCount() {
        return accounts.size();
    }

    public void addAccount(Account account) {
        accounts.put(account.getId(), account);
    }

    public Account getAccount(int id) {
        if (accountExists(id)) {
            return accounts.get(id);
        }
        return null;
    }

    public boolean accountExists(int id) {
        if (accounts.containsKey(id)) {
            return true;
        }
        return false;
    }

    public boolean checkAccountType(Account account, String type) {
        try {
            return account.checkType(type);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void transfer(int idFrom, int idTo, double transferAmount) {
        if (transferAmountLessThanBalance(transferAmount, getAccount(idFrom))) {
            getAccount(idFrom).withdraw(transferAmount);
            getAccount(idTo).deposit(transferAmount);
        } else {
            getAccount(idTo).deposit(getAccount(idFrom).getBalance());
            getAccount(idFrom).withdraw(transferAmount);
        }

    }

    public boolean transferAmountLessThanBalance(double transferAmount, Account account) {
        if (transferAmount > account.getBalance()) {
            return false;
        }
        return true;
    }

    public void passTimeForExistingAccounts(int timeAmount) {
        for (Account account : accounts.values()) {
            account.passTime(timeAmount);
        }
    }

    public void closeAccountsWithZeroBalance() {
        List<Integer> accountsToRemove = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getBalance() == 0) {
                accountsToRemove.add(account.getId());
            }
        }
        closeAccounts(accountsToRemove);
    }

    public void closeAccounts(List<Integer> accountsToRemove) {
        for (int id : accountsToRemove) {
            accounts.remove(id);
        }
    }

    public void minimumBalancePayment() {
        for (Account account : accounts.values()) {
            if (account.getBalance() < 100) {
                account.withdraw(MINIMUM_BALANCE_FEE);
            }
        }
    }

    public void accrueApr() {
        for (Account account : accounts.values()) {
            if (checkAccountType(account, "cd")) {
                calculateAprForCD(account);
            } else {
                calculateApr(account);
            }
        }
    }

    public void calculateApr(Account account) {
        double aprToDecimal = account.getApr() / 100;
        double calculatedApr = (aprToDecimal / 12) * account.getBalance();
        account.deposit(calculatedApr);
    }

    public void calculateAprForCD(Account account) {
        for (int i = 0; i < 4; i++) {
            calculateApr(account);
        }
    }


}
