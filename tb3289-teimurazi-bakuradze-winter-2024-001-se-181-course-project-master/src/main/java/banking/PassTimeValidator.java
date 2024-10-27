package banking;

public class PassTimeValidator extends CommandValidator {
    private int passTimeAmount;

    PassTimeValidator(Bank bank) {
        super(bank);
    }

    public boolean validatePassTime(String[] command) {
        if (command.length != 2) {
            return false;
        }
        getPassTimeAmount(command[1]);
        if (validatePassTimeAmount(passTimeAmount)) {
            return true;
        }
        return false;
    }

    public void getPassTimeAmount(String command) {
        this.passTimeAmount = Integer.parseInt(command);
    }


    public boolean validatePassTimeAmount(int passTimeAmount) {
        if (passTimeAmount < 1 || passTimeAmount > 60) {
            return false;
        }
        return true;
    }
}
