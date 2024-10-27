package banking;

public class CreateValidator extends CommandValidator {
    private String idString;
    private double apr;
    private int id;
    private String accountType;
    private double cdStartingBalance;

    public CreateValidator(Bank bank) {
        super(bank);
    }

    public boolean validateCreate(String[] command) {
        try {
            this.accountType = command[1];
            this.idString = command[2];
            this.apr = Double.valueOf(command[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        if (checkingAndSavingValidation(command)) {
            return true;
        }
        if (cdValidation(command)) {
            return true;
        }
        return false;
    }


    public boolean validateApr(double apr) {
        if (apr >= 0 && apr <= 10) {
            return true;
        }
        return false;
    }

    public boolean idValidator(String idString) {
        try {
            this.id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            return false;
        }
        if (idString == null || idString.length() != 8) {
            return false;
        }

        return !bank.accountExists(id);

    }

    public boolean cdStartingBalanceValidation(double cdStartingBalance) {
        if (cdStartingBalance < 1000 || cdStartingBalance > 10000) {
            return false;
        }
        return true;
    }

    public boolean checkingAndSavingValidation(String[] command) {
        if (("checking".equals(accountType) || "savings".equals(accountType)) && (idValidator(idString) && validateApr(apr) && command.length == 4)) {
            return true;
        }
        return false;
    }

    public boolean cdValidation(String[] command) {
        try {
            this.cdStartingBalance = Double.valueOf(command[4]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        if ("cd".equals(accountType) && idValidator(idString) && validateApr(apr) && cdStartingBalanceValidation(cdStartingBalance) && command.length == 5) {
            return true;
        }
        return false;
    }
}
