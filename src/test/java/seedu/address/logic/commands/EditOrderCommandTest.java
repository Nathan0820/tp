package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ORDER_A;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ORDER_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_5;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_READY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditOrderCommand.EditOrderDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.OrderBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditOrderCommand.
 */
public class EditOrderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws CommandException {
        Order firstOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order editedOrder = new OrderBuilder()
                .withCustomerId(firstOrder.getCustomerId())
                .withItem(VALID_ITEM_BURGER)
                .withQuantity(VALID_QUANTITY_5)
                .withDeliveryTime("2031-06-15 1400")
                .withAddress("Block 312, Amy Street 1")
                .withStatus(VALID_STATUS_READY)
                .build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withItem(VALID_ITEM_BURGER)
                .withQuantity(VALID_QUANTITY_5)
                .withDeliveryTime("2031-06-15 1400")
                .withAddress("Block 312, Amy Street 1")
                .withStatus(VALID_STATUS_READY)
                .build();
        EditOrderCommand editOrderCommand = new EditOrderCommand(INDEX_FIRST_ORDER, descriptor);

        Person customer = model.findPersonById(firstOrder.getCustomerId());
        String quantityWarning = String.format(EditOrderCommand.MESSAGE_QUANTITY_CHANGED_WARNING,
                VALID_QUANTITY_5, editedOrder.getStatus(), INDEX_FIRST_ORDER.getOneBased());
        String expectedMessage = quantityWarning
                + String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(editedOrder, customer.getName().toString()));

        CommandResult result = editOrderCommand.execute(model);
        assertEquals(new CommandResult(expectedMessage), result);
        assertEquals(editedOrder, model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased()));
    }

    @Test
    public void execute_deliveryTimeInPast_showsWarning() throws CommandException {
        model.updateFilteredOrderList(order -> true);
        Order firstOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withDeliveryTime("2000-01-01 1200")
                .build();
        EditOrderCommand editOrderCommand = new EditOrderCommand(INDEX_FIRST_ORDER, descriptor);

        Order editedOrder = new OrderBuilder(firstOrder)
                .withDeliveryTime("2000-01-01 1200")
                .build();
        Person customer = model.findPersonById(firstOrder.getCustomerId());
        String expectedMessage = AddOrderCommand.MESSAGE_PAST_TIME + "\n"
                + String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(editedOrder, customer.getName().toString()));

        CommandResult result = editOrderCommand.execute(model);
        assertEquals(new CommandResult(expectedMessage), result);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws CommandException {
        model.updateFilteredOrderList(order -> true);
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        Order lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        Order editedOrder = orderInList.withItem(VALID_ITEM_BURGER).withQuantity(VALID_QUANTITY_5).build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withItem(VALID_ITEM_BURGER)
                .withQuantity(VALID_QUANTITY_5)
                .build();
        EditOrderCommand editOrderCommand = new EditOrderCommand(indexLastOrder, descriptor);

        Person customer = model.findPersonById(lastOrder.getCustomerId());
        String quantityWarning = String.format(EditOrderCommand.MESSAGE_QUANTITY_CHANGED_WARNING,
                VALID_QUANTITY_5, editedOrder.getStatus(), indexLastOrder.getOneBased());
        String expectedMessage = quantityWarning
                + String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(editedOrder, customer.getName().toString()));

        CommandResult result = editOrderCommand.execute(model);
        assertEquals(new CommandResult(expectedMessage), result);
        assertEquals(editedOrder, model.getFilteredOrderList().get(indexLastOrder.getZeroBased()));
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() throws CommandException {
        EditOrderCommand editOrderCommand = new EditOrderCommand(INDEX_FIRST_ORDER, new EditOrderDescriptor());
        assertCommandFailure(editOrderCommand, model, EditOrderCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_filteredList_success() throws CommandException {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order editedOrder = new OrderBuilder(orderInFilteredList).withItem(VALID_ITEM_BURGER).build();
        EditOrderCommand editOrderCommand = new EditOrderCommand(INDEX_FIRST_ORDER,
                new EditOrderDescriptorBuilder().withItem(VALID_ITEM_BURGER).build());

        Person customer = model.findPersonById(orderInFilteredList.getCustomerId());
        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(editedOrder, customer.getName().toString()));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOrder(model.getFilteredOrderList().get(0), editedOrder);

        CommandResult result = editOrderCommand.execute(model);
        assertEquals(new CommandResult(expectedMessage), result);
    }

    @Test
    public void execute_invalidOrderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withItem(VALID_ITEM_BURGER).build();
        EditOrderCommand editOrderCommand = new EditOrderCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidOrderIndexFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);
        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getOrderList().size());

        EditOrderCommand editOrderCommand = new EditOrderCommand(outOfBoundIndex,
                new EditOrderDescriptorBuilder().withItem(VALID_ITEM_BURGER).build());

        assertCommandFailure(editOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditOrderCommand standardCommand = new EditOrderCommand(INDEX_FIRST_ORDER, DESC_ORDER_A);

        // same values -> returns true
        EditOrderDescriptor copyDescriptor = new EditOrderDescriptor(DESC_ORDER_A);
        EditOrderCommand commandWithSameValues = new EditOrderCommand(INDEX_FIRST_ORDER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditOrderCommand(INDEX_SECOND_ORDER, DESC_ORDER_A)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditOrderCommand(INDEX_FIRST_ORDER, DESC_ORDER_B)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();
        EditOrderCommand editOrderCommand = new EditOrderCommand(index, editOrderDescriptor);
        String expected = EditOrderCommand.class.getCanonicalName() + "{index=" + index + ", editOrderDescriptor="
                + editOrderDescriptor + "}";
        assertEquals(expected, editOrderCommand.toString());
    }
}
