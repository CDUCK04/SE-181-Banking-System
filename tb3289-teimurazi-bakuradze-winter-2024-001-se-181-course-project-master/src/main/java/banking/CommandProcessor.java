package banking;

import java.util.Locale;

public class CommandProcessor {
    protected Bank bank;
    protected String[] commandParts;
    private String operation;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }


    public void processCommand(String command) {
        getOperation(command);
        if ("create".equals(operation)) {
            CreateProcessor createProcessor = new CreateProcessor(bank);
            createProcessor.processCreate(commandParts);
        }
        if ("deposit".equals(operation)) {
            DepositProcessor depositProcessor = new DepositProcessor(bank);
            depositProcessor.processDeposit(commandParts);
        }
        if ("withdraw".equals(operation)) {
            WithdrawProcessor withdrawProcessor = new WithdrawProcessor(bank);
            withdrawProcessor.processWithdraw(commandParts);
        }
        if ("transfer".equals(operation)) {
            TransferProcessor transferProcessor = new TransferProcessor(bank);
            transferProcessor.processTransfer(commandParts);
        }
        if ("pass".equals(operation)) {
            PassTimeProcessor passTimeProcessor = new PassTimeProcessor(bank);
            passTimeProcessor.processPassTime(commandParts);
        }
    }


    public void getOperation(String command) {
        commandParts = command.toLowerCase(Locale.ROOT).split(" ");
        this.operation = commandParts[0];
    }


}
