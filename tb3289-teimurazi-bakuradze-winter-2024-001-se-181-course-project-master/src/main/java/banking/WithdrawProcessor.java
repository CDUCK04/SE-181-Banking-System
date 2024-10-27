package banking;

public class WithdrawProcessor extends CommandProcessor {
    private double withdrawAmount;
    private int accountId;

    WithdrawProcessor(Bank bank) {
        super(bank);
    }

    public void processWithdraw(String[] commandParts) {
        parseWithdrawCommand(commandParts);
        bank.getAccount(accountId).withdraw(withdrawAmount);
    }

    public void parseWithdrawCommand(String[] commandParts) {
        this.accountId = Integer.parseInt(commandParts[1]);
        this.withdrawAmount = Double.valueOf(commandParts[2]);

    }
}
