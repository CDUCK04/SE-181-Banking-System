package banking;

import java.util.Locale;

public class CommandValidator {
    protected Bank bank;
    protected String[] commandParts;
    protected String operation;
    private boolean isValid;

    public CommandValidator(Bank bank) {
        this.bank = bank;

    }

    public boolean validate(String command) {
        isValid = false;
        getOperation(command);
        if ("create".equals(operation)) {
            CreateValidator createValidator = new CreateValidator(bank);
            isValid = createValidator.validateCreate(commandParts);
        }
        if ("deposit".equals(operation)) {
            DepositValidator depositValidator = new DepositValidator(bank);
            isValid = depositValidator.validateDeposit(commandParts);
        }
        if ("withdraw".equals(operation)) {
            WithdrawValidator withdrawalValidator = new WithdrawValidator(bank);
            isValid = withdrawalValidator.validateWithdraw(commandParts);
        }
        if ("transfer".equals(operation)) {
            TransferValidator transferValidator = new TransferValidator(bank);
            isValid = transferValidator.validateTransfer(commandParts);
        }
        if ("pass".equals(operation)) {
            PassTimeValidator passTimeValidator = new PassTimeValidator(bank);
            isValid = passTimeValidator.validatePassTime(commandParts);
        }
        return isValid;
    }


    public void getOperation(String command) {
        command = command.toLowerCase(Locale.ROOT);
        this.commandParts = command.split(" ");
        operation = commandParts[0];
    }
}
