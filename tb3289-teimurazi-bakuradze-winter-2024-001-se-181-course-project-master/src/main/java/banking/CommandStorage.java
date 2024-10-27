package banking;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class CommandStorage {
    private List<String> invalidCommands = new ArrayList<>();
    private LinkedHashMap<Integer, List<String>> validCommands = new LinkedHashMap<>();
    private int id;
    private int idFrom;
    private int idTo;
    private String[] commandParts;

    public void addInvalidCommand(String command) {
        this.invalidCommands.add(command);
    }

    public void storeValidCommand(String command) {
        getId(command);
    }

    public void addValidCommand(int id, String command) {
        this.validCommands.get(id).add(command);
    }

    public LinkedHashMap<Integer, List<String>> getValidCommands() {
        return validCommands;
    }

    public List<String> getInvalidCommands() {
        return invalidCommands;
    }


    public void getId(String command) {
        this.commandParts = command.toLowerCase(Locale.ROOT).split(" ");
        if (commandParts[0].equals("create")) {
            this.id = Integer.parseInt(commandParts[2]);
            createAccountCommands(id);
        }
        if (commandParts[0].equals("withdraw") || commandParts[0].equals("deposit")) {
            this.id = Integer.parseInt(commandParts[1]);
            addValidCommand(id, command);
        }
        if (commandParts[0].equals("transfer")) {
            this.idFrom = Integer.parseInt(commandParts[1]);
            this.idTo = Integer.parseInt(commandParts[2]);
            addValidCommand(idFrom, command);
            addValidCommand(idTo, command);
        }
    }

    public void createAccountCommands(int id) {
        validCommands.put(id, new ArrayList<>());
    }
}
