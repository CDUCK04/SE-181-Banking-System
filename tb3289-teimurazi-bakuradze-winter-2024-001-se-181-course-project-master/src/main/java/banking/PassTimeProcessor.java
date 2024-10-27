package banking;

public class PassTimeProcessor extends CommandProcessor {
    private int timeToPass;

    PassTimeProcessor(Bank bank) {
        super(bank);
    }

    public void processPassTime(String[] commandParts) {
        this.timeToPass = Integer.parseInt(commandParts[1]);
        bank.passTimeForExistingAccounts(timeToPass);
        for (int i = 0; i < timeToPass; i++) {
            bank.closeAccountsWithZeroBalance();
            bank.minimumBalancePayment();
            bank.accrueApr();
        }
    }


}
