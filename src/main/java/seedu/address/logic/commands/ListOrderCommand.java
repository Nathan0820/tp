package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import seedu.address.model.Model;

/**
 * Lists all orders in the address book to the user.
 */
public class ListOrderCommand extends Command {

    public static final String COMMAND_WORD = "list-o";

    public static final String MESSAGE_SUCCESS = "Listed all orders!";
    public static final String MESSAGE_EMPTY = "Order list is empty.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        if (model.getFilteredOrderList().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        } else {
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
