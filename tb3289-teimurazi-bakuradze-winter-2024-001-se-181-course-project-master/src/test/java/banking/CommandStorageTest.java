package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandStorageTest {

    CommandStorage commandStorage;
    List<String> expectedAnswer;

    @BeforeEach
    void setUp() {
        expectedAnswer = new ArrayList<>();
        commandStorage = new CommandStorage();
    }

    @Test
    void invalid_command_is_stored() {
        commandStorage.addInvalidCommand("create cdh 12345678 1");
        expectedAnswer.add("create cdh 12345678 1");
        List<String> actual = commandStorage.getInvalidCommands();

        assertEquals(expectedAnswer, actual);
    }

    @Test
    void multiple_invalid_command_is_stored() {
        commandStorage.addInvalidCommand("create cdh 12345678 1");
        commandStorage.addInvalidCommand("create cdh 12345678 2");
        expectedAnswer.add("create cdh 12345678 1");
        expectedAnswer.add("create cdh 12345678 2");
        List<String> actual = commandStorage.getInvalidCommands();

        assertEquals(expectedAnswer, actual);
    }

    @Test
    void valid_command_is_stored() {
        commandStorage.storeValidCommand("create checking 12345678 1");
        commandStorage.storeValidCommand("deposit 12345678 100");
        expectedAnswer.add("deposit 12345678 100");
        List<String> actual = commandStorage.getValidCommands().get(12345678);

        assertEquals(expectedAnswer, actual);
    }

    @Test
    void multiple_valid_commands_are_stored() {
        commandStorage.storeValidCommand("create checking 12345678 1");
        commandStorage.storeValidCommand("deposit 12345678 100");
        commandStorage.storeValidCommand("Withdraw 12345678 100");
        expectedAnswer.add("deposit 12345678 100");
        expectedAnswer.add("Withdraw 12345678 100");
        List<String> actual = commandStorage.getValidCommands().get(12345678);

        assertEquals(expectedAnswer, actual);
    }

}
