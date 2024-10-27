package banking;

public class WithdrawValidator extends CommandValidator {
    private double withdrawAmount;
    private int id;

    public WithdrawValidator(Bank bank) {
        super(bank);
    }

    public boolean validateWithdraw(String[] commandPart) {
        try {
            this.id = Integer.parseInt(commandPart[1]);
            this.withdrawAmount = Double.valueOf(commandPart[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        if (withdrawValidation(commandPart)) {
            return true;
        }

        return false;
    }

    public boolean withdrawValidation(String[] command) {
        if (bank.accountExists(id) && withdrawAmountValidation(id, withdrawAmount) && command.length == 3) {
            return bank.getAccount(id).withdrawAvailable();
        }
        return false;
    }

    public boolean withdrawAmountValidation(int id, double withdrawAmount) {
        if (bank.getAccount(id).isValidWithdrawAmount(withdrawAmount)) {
            return true;
        }
        return false;
    }


}
