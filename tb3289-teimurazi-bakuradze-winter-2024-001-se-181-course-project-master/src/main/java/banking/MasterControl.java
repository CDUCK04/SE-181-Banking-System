package banking;

import java.util.List;

public class MasterControl {
    private CommandValidator commandValidator;
    private CommandProcessor commandProcessor;
    private CommandStorage commandStorage;
    private Output output;

    public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor, CommandStorage commandStorage, Output output) {
        this.commandValidator = commandValidator;
        this.commandProcessor = commandProcessor;
        this.commandStorage = commandStorage;
        this.output = output;
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (commandValidator.validate(command)) {
                commandProcessor.processCommand(command);
                commandStorage.storeValidCommand(command);
            } else {
                commandStorage.addInvalidCommand(command);
            }
        }
        return output.getFormattedOutput();
    }
}
