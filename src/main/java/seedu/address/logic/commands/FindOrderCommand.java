package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderContainsKeywordsPredicate;

/**
 * Finds and lists all orders in address book whose item, address, or customerId
 * contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindOrderCommand extends Command {

    public static final String COMMAND_WORD = "find-o";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all orders whose item, address, status, "
            + "or customerId contain the specified search phrase (case-insensitive) "
            + "and displays the number of matching orders.\n"
            + "Parameters: i/ITEM_NAME | a/ADDRESS | c/CUSTOMER_ID | s/STATUS\n"
            + "Example: " + COMMAND_WORD + " i/pizza";

    private final OrderContainsKeywordsPredicate predicate;

    public FindOrderCommand(OrderContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    public Predicate<Order> getPredicate() {
        return predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (predicate.getSearchType() == OrderContainsKeywordsPredicate.SearchType.CUSTOMER) {
            String keyword = predicate.getKeyword();
            String customerUuid;

            // Check if keyword is a UUID string (contains hyphens) or an index number
            if (keyword.contains("-")) {
                // It's already a UUID string
                customerUuid = keyword;
            } else {
                // It's an index number, convert to UUID
                int oneBased = Integer.parseInt(keyword);
                int zeroBased = oneBased - 1;

                if (zeroBased < 0 || zeroBased >= model.getFilteredPersonList().size()) {
                    throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }

                customerUuid = model.getFilteredPersonList().get(zeroBased).getId().toString();
            }

            model.updateFilteredOrderList(
                    new OrderContainsKeywordsPredicate(OrderContainsKeywordsPredicate.SearchType.CUSTOMER, customerUuid)
            );
        } else {
            model.updateFilteredOrderList(predicate);
        }

        int foundCount = model.getFilteredOrderList().size();
        String resultMessage;
        if (foundCount == 0) {
            resultMessage = "No orders found.";
        } else if (foundCount == 1) {
            resultMessage = "1 order found.";
        } else {
            resultMessage = foundCount + " orders found.";
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
