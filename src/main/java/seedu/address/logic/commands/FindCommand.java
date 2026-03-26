package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names, phone numbers, "
            + "email addresses, addresses, or tags contain the specified search phrase (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: SEARCH_PHRASE\n"
            + "Example: " + COMMAND_WORD + " Blk 30 \n"
            + "Example: " + COMMAND_WORD + " t/VIP \n";

    private static final Logger logger = LogsCenter.getLogger(FindCommand.class);

    private final PersonContainsKeywordsPredicate predicate;

    /**
     * Creates a {@code FindCommand} with the given search predicate.
     */
    public FindCommand(PersonContainsKeywordsPredicate predicate) {
        requireNonNull(predicate, "Find predicate cannot be null");
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model, "Model cannot be null");
        assert predicate != null : "Predicate must be non-null after construction";
        model.updateFilteredPersonList(predicate);
        int listSize = model.getFilteredPersonList().size();
        assert listSize >= 0 : "Filtered list size cannot be negative";
        String resultMessage = String.format(
                Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                listSize,
                predicate.getSummary()
        );
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
