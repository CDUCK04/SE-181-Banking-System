package banking;

public class CreateProcessor extends CommandProcessor {
    private String accountType;
    private double apr;
    private double cdStartingBalance;
    private int accountId;


    CreateProcessor(Bank bank) {
        super(bank);
    }

    public void processCreate(String[] commandParts) {
        parseCreateCommand(commandParts);
        if ("checking".equals(accountType)) {
            bank.addAccount(new Checking(accountId, apr));
        }
        if ("savings".equals(accountType)) {
            bank.addAccount(new Savings(accountId, apr));
        }
        if ("cd".equals(accountType)) {
            this.cdStartingBalance = Double.valueOf(commandParts[4]);
            bank.addAccount(new CD(accountId, apr, cdStartingBalance));
        }

    }


    public void parseCreateCommand(String[] commandParts) {
        this.accountType = commandParts[1];
        this.accountId = Integer.parseInt(commandParts[2]);
        this.apr = Double.valueOf(commandParts[3]);


    }
}
