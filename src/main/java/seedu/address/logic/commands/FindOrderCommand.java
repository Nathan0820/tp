package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.order.OrderContainsKeywordsPredicate;

/**
 * Finds and lists all orders in address book whose item, address, or customerId
 * contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindOrderCommand extends Command {

    public static final String COMMAND_WORD = "find-o";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all orders whose item, address, "
            + "or customerId contain the specified search phrase (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: i/ITEM_NAME | a/ADDRESS | c/CUSTOMER_ID\n"
            + "Example: " + COMMAND_WORD + " i/pizza";

    private final OrderContainsKeywordsPredicate predicate;

    public FindOrderCommand(OrderContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredOrderList(predicate);

        String resultMessage = "=== FIND ORDERS ===\n";
        if (model.getFilteredOrderList().isEmpty()) {
            resultMessage += "No orders found.";
        } else {
            resultMessage += model.getFilteredOrderList().toString();
        }
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindOrderCommand)) {
            return false;
        }

        FindOrderCommand otherFindOrderCommand = (FindOrderCommand) other;
        return predicate.equals(otherFindOrderCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
