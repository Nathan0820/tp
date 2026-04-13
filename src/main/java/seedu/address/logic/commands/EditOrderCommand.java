package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
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
 * Edits the details of an existing order in the address book.
 */
public class EditOrderCommand extends Command {

    public static final String COMMAND_WORD = "edit-o";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the order identified "
            + "by the index number used in the displayed order list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ITEM + "ITEM] "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ITEM + "Pizza "
            + PREFIX_QUANTITY + "3 "
            + PREFIX_DATETIME + "2026-04-02 1200 "
            + PREFIX_ADDRESS + "123 Jurong West St 42, #05-01 "
            + PREFIX_STATUS + "PREPARING";

    public static final String MESSAGE_EDIT_ORDER_SUCCESS = "Edited Order: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_UNKNOWN_CUSTOMER = "The customer for this order is unknown.";
    public static final String MESSAGE_QUANTITY_CHANGED_WARNING = "WARNING: The quantity "
            + "has been changed to %1$s. Are you sure the status is still %2$s? "
            + "If not, please use the command 'edit-o %3$d s/[NEW_STATUS]' to update the status.\n";

    private final Index index;
    private final EditOrderDescriptor editOrderDescriptor;

    /**
     * @param index of the order in the filtered order list to edit
     * @param editOrderDescriptor details to edit the order with
     */
    public EditOrderCommand(Index index, EditOrderDescriptor editOrderDescriptor) {
        requireNonNull(index);
        requireNonNull(editOrderDescriptor);

        this.index = index;
        this.editOrderDescriptor = new EditOrderDescriptor(editOrderDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!editOrderDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        List<Order> lastShownList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToEdit = lastShownList.get(index.getZeroBased());
        Order editedOrder = createEditedOrder(orderToEdit, editOrderDescriptor);

        Person customer = model.findPersonById(orderToEdit.getCustomerId());
        if (customer == null) {
            throw new CommandException(MESSAGE_UNKNOWN_CUSTOMER);
        }

        model.setOrder(orderToEdit, editedOrder);

        String customerName = customer.getName().toString();
        String resultMessage = "";

        if (editOrderDescriptor.getQuantity().isPresent()) {
            Quantity newQuantity = editOrderDescriptor.getQuantity().get();
            if (newQuantity.isLarge()) {
                resultMessage += AddOrderCommand.MESSAGE_LARGE_QUANTITY_WARNING + "\n";
            }
        }

        if (editOrderDescriptor.getDeliveryTime().isPresent()
                && !editedOrder.getDeliveryTime().isInFuture()) {
            resultMessage += AddOrderCommand.MESSAGE_PAST_TIME + "\n";
        }

        if (editOrderDescriptor.getQuantity().isPresent()
                && !orderToEdit.getQuantity().equals(editOrderDescriptor.getQuantity().get())) {
            resultMessage += String.format(
                MESSAGE_QUANTITY_CHANGED_WARNING,
                editOrderDescriptor.getQuantity().get().value,
                editedOrder.getStatus(),
                index.getOneBased()
            );
        }
        resultMessage += String.format(
            MESSAGE_EDIT_ORDER_SUCCESS,
            Messages.format(editedOrder, customerName)
        );
        return new CommandResult(resultMessage);
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToEdit}
     * edited with {@code editOrderDescriptor}.
     */
    private static Order createEditedOrder(Order orderToEdit, EditOrderDescriptor editOrderDescriptor) {

        Item updatedItem = editOrderDescriptor.getItem().orElse(orderToEdit.getItem());
        Quantity updatedQuantity = editOrderDescriptor.getQuantity().orElse(orderToEdit.getQuantity());
        DeliveryTime updatedDeliveryTime = editOrderDescriptor.getDeliveryTime().orElse(orderToEdit.getDeliveryTime());
        Address updatedAddress = editOrderDescriptor.getAddress().orElse(orderToEdit.getAddress());
        Status updatedStatus = editOrderDescriptor.getStatus().orElse(orderToEdit.getStatus());

        return new Order(orderToEdit.getCustomerId(),
            updatedItem,
            updatedQuantity,
            updatedDeliveryTime,
            updatedAddress,
            updatedStatus
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditOrderCommand)) {
            return false;
        }

        EditOrderCommand otherEditOrderCommand = (EditOrderCommand) other;
        return index.equals(otherEditOrderCommand.index)
                && editOrderDescriptor.equals(otherEditOrderCommand.editOrderDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editOrderDescriptor", editOrderDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class EditOrderDescriptor {
        private Item item;
        private Quantity quantity;
        private DeliveryTime deliveryTime;
        private Address address;
        private Status status;

        public EditOrderDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditOrderDescriptor(EditOrderDescriptor toCopy) {
            setItem(toCopy.item);
            setQuantity(toCopy.quantity);
            setDeliveryTime(toCopy.deliveryTime);
            setAddress(toCopy.address);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(item, quantity, deliveryTime, address, status);
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public Optional<Item> getItem() {
            return Optional.ofNullable(item);
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setDeliveryTime(DeliveryTime deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public Optional<DeliveryTime> getDeliveryTime() {
            return Optional.ofNullable(deliveryTime);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOrderDescriptor)) {
                return false;
            }

            EditOrderDescriptor otherEditOrderDescriptor = (EditOrderDescriptor) other;
            return Objects.equals(item, otherEditOrderDescriptor.item)
                    && Objects.equals(quantity, otherEditOrderDescriptor.quantity)
                    && Objects.equals(deliveryTime, otherEditOrderDescriptor.deliveryTime)
                    && Objects.equals(address, otherEditOrderDescriptor.address)
                    && Objects.equals(status, otherEditOrderDescriptor.status);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("item", item)
                    .add("quantity", quantity)
                    .add("deliveryTime", deliveryTime)
                    .add("address", address)
                    .add("status", status)
                    .toString();
        }
    }
}
