package banking;

public class TransferValidator extends CommandValidator {
    private int idFrom;
    private int idTo;
    private double transferAmount;


    TransferValidator(Bank bank) {
        super(bank);
    }

    public boolean validateTransfer(String[] command) {
        try {
            this.idFrom = Integer.parseInt(command[1]);
            this.idTo = Integer.parseInt(command[2]);
            this.transferAmount = Integer.parseInt(command[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        if (!bank.checkAccountType(bank.getAccount(idFrom), "cd") && !bank.checkAccountType(bank.getAccount(idTo), "cd") && transferValidation(command)) {
            return true;
        }

        return false;
    }

    public boolean transferValidation(String[] command) {

        if (bank.accountExists(idFrom) && bank.accountExists(idTo) && transferAmountValidation() && command.length == 4 && idFrom != idTo) {
            return bank.getAccount(idFrom).withdrawAvailable();
        }
        return false;
    }

    public boolean transferAmountValidation() {
        if (transferFromValidation()) {
            return true;
        }
        return false;
    }


    public boolean transferFromValidation() {
        if (bank.getAccount(idFrom).isValidWithdrawAmount(transferAmount)) {
            return true;
        }
        return false;
    }


}
