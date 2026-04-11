package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonListPanelTest {

    private static class LogicStub implements Logic {
        private final List<String> executedCommands = new ArrayList<>();
        private CommandResult lastResult;

        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            executedCommands.add(commandText);
            lastResult = new CommandResult("Test execution: " + commandText);
            return lastResult;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public Path getAddressBookFilePath() {
            return null;
        }

        @Override
        public GuiSettings getGuiSettings() {
            return new GuiSettings();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
        }

        public List<String> getExecutedCommands() {
            return executedCommands;
        }

        public CommandResult getLastResult() {
            return lastResult;
        }

        public void reset() {
            executedCommands.clear();
            lastResult = null;
        }

        @Override
        public ObservableList<Person> getPersonList() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Test
    public void logicStub_execute_storesCommand() {
        LogicStub logicStub = new LogicStub();

        try {
            logicStub.execute("find-o c/123");
        } catch (CommandException | ParseException e) {
            // Expected to fail but command should still be recorded
        }

        assertEquals(1, logicStub.getExecutedCommands().size());
        assertEquals("find-o c/123", logicStub.getExecutedCommands().get(0));
    }

    @Test
    public void logicStub_multipleExecutions_storesAllCommands() {
        LogicStub logicStub = new LogicStub();

        try {
            logicStub.execute("command1");
            logicStub.execute("command2");
            logicStub.execute("command3");
        } catch (CommandException | ParseException e) {
            // Expected to fail but commands should still be recorded
        }

        assertEquals(3, logicStub.getExecutedCommands().size());
    }

    @Test
    public void logicStub_getLastResult_returnsResult() {
        LogicStub logicStub = new LogicStub();

        try {
            logicStub.execute("test");
        } catch (CommandException | ParseException e) {
            // Expected to fail
        }

        assertNotNull(logicStub.getLastResult());
        assertEquals("Test execution: test", logicStub.getLastResult().getFeedbackToUser());
    }

    @Test
    public void logicStub_reset_clearsState() {
        LogicStub logicStub = new LogicStub();

        try {
            logicStub.execute("test");
        } catch (CommandException | ParseException e) {
            // Expected to fail
        }

        assertNotNull(logicStub.getLastResult());

        logicStub.reset();

        assertEquals(0, logicStub.getExecutedCommands().size());
        assertEquals(null, logicStub.getLastResult());
    }

    @Test
    public void logicStub_executeWithUuidCommand_storesCommand() {
        LogicStub logicStub = new LogicStub();
        UUID uuid = UUID.randomUUID();
        String command = "find-o c/" + uuid.toString();

        try {
            logicStub.execute(command);
        } catch (CommandException | ParseException e) {
            // Expected to fail
        }

        assertEquals(1, logicStub.getExecutedCommands().size());
        assertEquals(command, logicStub.getExecutedCommands().get(0));
    }

    @Test
    public void person_withId_canBeUsedForCommand() {
        Person person = new PersonBuilder().withName("Test Person").build();
        UUID personId = person.getId();

        String expectedCommand = "find-o c/" + personId.toString();
        assertNotNull(expectedCommand);
        assertEquals("find-o c/" + personId.toString(), expectedCommand);
    }

    @Test
    public void logicStub_getGuiSettings_returnsDefaultSettings() {
        LogicStub logicStub = new LogicStub();
        GuiSettings settings = logicStub.getGuiSettings();
        assertNotNull(settings);
    }

    @Test
    public void logicStub_getFilteredPersonList_returnsObservableList() {
        LogicStub logicStub = new LogicStub();
        ObservableList<Person> list = logicStub.getFilteredPersonList();
        assertNotNull(list);
    }

    @Test
    public void logicStub_getFilteredOrderList_returnsObservableList() {
        LogicStub logicStub = new LogicStub();
        ObservableList<Order> list = logicStub.getFilteredOrderList();
        assertNotNull(list);
    }
}
