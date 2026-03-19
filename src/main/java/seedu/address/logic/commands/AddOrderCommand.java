package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_NO_SAVED_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Order;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;

/**
 * Adds an order to the address book.
 */
public class AddOrderCommand extends Command {

    public static final String COMMAND_WORD = "order";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new order to a specific customer.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_ITEM + "ITEM_NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_DATETIME + "DELIVERY_TIME "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_ITEM + "Pizza "
            + PREFIX_QUANTITY + "3 "
            + PREFIX_DATETIME + "2026-04-02 1200 "
            + PREFIX_ADDRESS + "123 Jurong West St 42, #05-01 "
            + PREFIX_STATUS + "PREPARING";

    public static final String MESSAGE_SUCCESS = "New order added: %1$s";

    private final Index customerIndex;
    private final Item item;
    private final Quantity quantity;
    private final DeliveryTime deliveryTime;
    private final Optional<Address> address;
    private final Optional<Status> status;

    private Order toAdd;

    /**
     * Creates an AddOrderCommand to add the specified {@code order}
     */
    public AddOrderCommand(Index index, Item item, Quantity quantity, DeliveryTime deliveryTime,
            Optional<Address> addressOptional, Optional<Status> statusOptional) {
        requireNonNull(index);
        requireNonNull(item);
        requireNonNull(quantity);
        requireNonNull(deliveryTime);
        requireNonNull(addressOptional);
        requireNonNull(statusOptional);

        this.customerIndex = index;
        this.item = item;
        this.quantity = quantity;
        this.deliveryTime = deliveryTime;
        this.address = addressOptional;
        this.status = statusOptional;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (customerIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person customer = lastShownList.get(customerIndex.getZeroBased());

        // Resolve optional fields
        Address finalAddress;
        if (address.isPresent()) {
            finalAddress = address.get();
        } else if (customer.getAddress().isPresent()) {
            finalAddress = customer.getAddress().get();
        } else {
            throw new CommandException(MESSAGE_NO_SAVED_ADDRESS);
        }

        Status finalStatus = status.orElse(Status.DEFAULT_STATUS);

        UUID customerId = customer.getId();

        toAdd = new Order(customerId, item, quantity, deliveryTime, finalAddress, finalStatus);

        model.addOrder(toAdd);

        String customerName = customer.getName().fullName;

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd, customerName)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddOrderCommand)) {
            return false;
        }

        AddOrderCommand otherAddOrderCommand = (AddOrderCommand) other;
        return customerIndex.equals(otherAddOrderCommand.customerIndex)
                && item.equals(otherAddOrderCommand.item)
                && quantity.equals(otherAddOrderCommand.quantity)
                && deliveryTime.equals(otherAddOrderCommand.deliveryTime)
                && address.equals(otherAddOrderCommand.address)
                && status.equals(otherAddOrderCommand.status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}

