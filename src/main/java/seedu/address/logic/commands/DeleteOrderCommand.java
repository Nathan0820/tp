package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * Deletes an order identified using it's displayed index from the address book.
 */
public class DeleteOrderCommand extends Command {

    public static final String COMMAND_WORD = "delete-o";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the order identified by the index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ORDER_SUCCESS = "Deleted Order: %1$s";

    private static final Logger logger = Logger.getLogger(DeleteOrderCommand.class.getName());

    private final Index targetIndex;

    public DeleteOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.log(Level.INFO, "Executing DeleteOrderCommand for index: " + targetIndex);

        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.log(Level.WARNING, "Invalid order index: " + targetIndex);
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToDelete = lastShownList.get(targetIndex.getZeroBased());
        assert orderToDelete != null : "Order should exist after index validation";

        logger.log(Level.INFO, "Deleting order: " + orderToDelete);
        model.deleteOrder(orderToDelete);

        // Get the customer name for formatting
        UUID customerId = orderToDelete.getCustomerId();
        Person customer = model.findPersonById(customerId);
        assert customer != null : "Customer should exist for the given order";
        String customerName = customer.getName().fullName;

        return new CommandResult(
                String.format(MESSAGE_DELETE_ORDER_SUCCESS, Messages.format(orderToDelete, customerName)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteOrderCommand)) {
            return false;
        }

        DeleteOrderCommand otherDeleteOrderCommand = (DeleteOrderCommand) other;
        return targetIndex.equals(otherDeleteOrderCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
