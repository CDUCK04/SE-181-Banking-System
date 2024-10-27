package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Output {
    private Bank bank;
    private CommandStorage commandStorage;
    private LinkedHashMap<Integer, List<String>> accountValidCommandList;
    private List<Integer> idList;
    private List<String> invalidCommandList;
    private List<String> finalList;

    Output(CommandStorage commandStorage, Bank bank) {
        this.commandStorage = commandStorage;
        this.bank = bank;
        finalList = new ArrayList<>();
    }

    public void removeDeletedAccounts() {
        idList = new ArrayList<>(commandStorage.getValidCommands().keySet());
        for (int id : idList) {
            if (!bank.accountExists(id)) {
                commandStorage.getValidCommands().remove(id);
            }
        }
    }

    public String getAccountStatus(int id) {
        String accountStatus;
        String accountType;
        String idString;
        String apr;
        String balance;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);

        accountType = (bank.getAccount(id).getAccountType());
        idString = String.valueOf(id);
        apr = decimalFormat.format(bank.getAccount(id).getApr());
        balance = decimalFormat.format(bank.getAccount(id).getBalance());

        accountStatus = (accountType + " " + idString + " " + balance + " " + apr);

        return accountStatus;
    }

    public List<String> getFormattedOutput() {
        removeDeletedAccounts();
        idList = new ArrayList<>(commandStorage.getValidCommands().keySet());
        accountValidCommandList = commandStorage.getValidCommands();
        invalidCommandList = commandStorage.getInvalidCommands();

        for (int i : idList) {
            finalList.add(getAccountStatus(i));
            finalList.addAll(accountValidCommandList.get(i));
        }

        finalList.addAll(invalidCommandList);
        return finalList;

    }


}
