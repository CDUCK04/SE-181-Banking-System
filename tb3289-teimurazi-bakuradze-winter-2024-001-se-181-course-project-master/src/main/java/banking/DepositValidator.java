package banking;

public class DepositValidator extends CommandValidator {
    private double depositAmount;
    private int id;

    public DepositValidator(Bank bank) {
        super(bank);
    }

    public boolean validateDeposit(String[] command) {
        try {
            this.id = Integer.parseInt(command[1]);
            this.depositAmount = Double.valueOf(command[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        if (depositValidation(command)) {
            return true;
        }

        return false;
    }

    public boolean depositValidation(String[] command) {
        if (bank.accountExists(id) && depositAmountValidation(id, depositAmount) && command.length == 3) {
            return true;
        }
        return false;
    }

    public boolean depositAmountValidation(int id, double depositAmount) {
        if (bank.getAccount(id).isValidDepositAmount(depositAmount)) {
            return true;
        }
        return false;
    }

}
