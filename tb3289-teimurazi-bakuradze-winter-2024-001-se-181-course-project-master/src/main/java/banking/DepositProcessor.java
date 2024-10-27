package banking;

public class DepositProcessor extends CommandProcessor {
    private double depositAmount;
    private int accountId;


    DepositProcessor(Bank bank) {
        super(bank);
    }

    public void processDeposit(String[] commandParts) {
        parseDepositCommand(commandParts);
        bank.getAccount(accountId).deposit(depositAmount);
    }

    public void parseDepositCommand(String[] commandParts) {
        this.accountId = Integer.parseInt(commandParts[1]);
        this.depositAmount = Double.valueOf(commandParts[2]);

    }
}
