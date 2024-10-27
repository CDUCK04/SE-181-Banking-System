package banking;

public class TransferProcessor extends CommandProcessor {
    private int idFrom;
    private int idTo;
    private double transferAmount;


    TransferProcessor(Bank bank) {
        super(bank);
    }

    public void processTransfer(String[] commandParts) {
        parseTransferCommand(commandParts);
        bank.transfer(idFrom, idTo, transferAmount);
    }

    public void parseTransferCommand(String[] commandParts) {
        this.idFrom = Integer.parseInt(commandParts[1]);
        this.idTo = Integer.parseInt(commandParts[2]);
        this.transferAmount = Double.valueOf(commandParts[3]);

    }
}
