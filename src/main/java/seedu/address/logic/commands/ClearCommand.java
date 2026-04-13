package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all customers and their orders.\n"
            + "Format: clear OR clear CONFIRM";
    public static final String MESSAGE_CONFIRMATION_REQUIRED = "Are you sure you want to clear all data in BZNUS?\n"
            + "This action cannot be undone. Type 'clear CONFIRM' to confirm.\n"
            + "'clear CONFIRM' is a standalone command and does not require a prior 'clear'.";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    private final boolean isConfirmed;

    public ClearCommand(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public ClearCommand() {
        this(false);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (!isConfirmed) {
            return new CommandResult(MESSAGE_CONFIRMATION_REQUIRED);
        }
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClearCommand)) {
            return false;
        }

        ClearCommand otherClearCommand = (ClearCommand) other;
        return isConfirmed == otherClearCommand.isConfirmed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isConfirmed);
    }
}
