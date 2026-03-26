package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalOrders.ORDER_A;
import static seedu.address.testutil.TypicalOrders.ORDER_B;
import static seedu.address.testutil.TypicalOrders.ORDER_C;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.OrderContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class FindOrderCommandTest {

    private Map<OrderContainsKeywordsPredicate.SearchType, String> createMap(
            OrderContainsKeywordsPredicate.SearchType type, String value) {
        return Collections.singletonMap(type, value);
    }
    @Test
    public void execute_validItemKeyword_commandExecutes() throws CommandException {
        Model model = new ModelManager(
                new AddressBookBuilder().withOrder(ORDER_A).withOrder(ORDER_B).build(),
                new UserPrefs());

        FindOrderCommand command = new FindOrderCommand(
                new OrderContainsKeywordsPredicate(createMap(OrderContainsKeywordsPredicate.SearchType.ITEM, "Pizza")));

        CommandResult result = command.execute(model);

        assertNotNull(result);
        assertEquals("1 order found.", result.getFeedbackToUser());
    }

    @Test
    public void execute_validAddressKeyword_commandExecutes() throws CommandException {
        Model model = new ModelManager(
                new AddressBookBuilder().withOrder(ORDER_A).withOrder(ORDER_B).build(),
                new UserPrefs());

        FindOrderCommand command = new FindOrderCommand(
                new OrderContainsKeywordsPredicate(createMap(
                        OrderContainsKeywordsPredicate.SearchType.ADDRESS, "Amy")));

        CommandResult result = command.execute(model);

        assertNotNull(result);
        assertTrue(result.getFeedbackToUser().contains("order found"));
    }

    @Test
    public void execute_validCustomerId_commandExecutes() throws CommandException {
        Model model = new ModelManager(
                new AddressBookBuilder().withOrder(ORDER_A).withOrder(ORDER_B).build(),
                new UserPrefs());

        String customerUuid = ORDER_A.getCustomerId().toString();

        FindOrderCommand command = new FindOrderCommand(
                new OrderContainsKeywordsPredicate(createMap(
                        OrderContainsKeywordsPredicate.SearchType.CUSTOMER, customerUuid)));

        CommandResult result = command.execute(model);

        assertNotNull(result);
        assertTrue(result.getFeedbackToUser().contains("order found"));
    }

    @Test
    public void execute_validCustomerIndex_success() throws Exception {
        Model model = new ModelManager(
                new AddressBookBuilder().withPerson(ALICE).withOrder(ORDER_A)
                        .withOrder(ORDER_B).withOrder(ORDER_C).build(), new UserPrefs());

        FindOrderCommand command = new FindOrderCommand(new OrderContainsKeywordsPredicate(createMap(
                OrderContainsKeywordsPredicate.SearchType.CUSTOMER, "1")));

        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("orders found"));
    }

    @Test
    public void execute_validOrderStatus_commandExecutes() throws CommandException {
        Model model = new ModelManager(
                new AddressBookBuilder().withOrder(ORDER_A).withOrder(ORDER_B).build(),
                new UserPrefs());

        FindOrderCommand command = new FindOrderCommand(
                new OrderContainsKeywordsPredicate(createMap(
                        OrderContainsKeywordsPredicate.SearchType.STATUS, "PREPARING")));

        CommandResult result = command.execute(model);

        assertNotNull(result);
        assertEquals("2 orders found.", result.getFeedbackToUser());
    }

    @Test
    public void execute_multiplePrefixes_commandExecutes() throws CommandException {
        Model model = new ModelManager(
                new AddressBookBuilder().withOrder(ORDER_A).withOrder(ORDER_B).build(),
                new UserPrefs());

        Map<OrderContainsKeywordsPredicate.SearchType, String> multiMap = new HashMap<>();
        multiMap.put(OrderContainsKeywordsPredicate.SearchType.ITEM, "Pizza");
        multiMap.put(OrderContainsKeywordsPredicate.SearchType.STATUS, "PREPARING");

        FindOrderCommand command = new FindOrderCommand(new OrderContainsKeywordsPredicate(multiMap));
        CommandResult result = command.execute(model);

        assertNotNull(result);
        assertTrue(result.getFeedbackToUser().contains("order found"));
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(createMap(
                OrderContainsKeywordsPredicate.SearchType.ITEM, "pizza"));
        FindOrderCommand command1 = new FindOrderCommand(predicate);
        FindOrderCommand command2 = new FindOrderCommand(predicate);
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentPredicate_returnsFalse() {
        OrderContainsKeywordsPredicate predicate1 = new OrderContainsKeywordsPredicate(createMap(
                OrderContainsKeywordsPredicate.SearchType.ITEM, "pizza"));
        OrderContainsKeywordsPredicate predicate2 = new OrderContainsKeywordsPredicate(createMap(
                OrderContainsKeywordsPredicate.SearchType.ADDRESS, "Jurong"));
        FindOrderCommand command1 = new FindOrderCommand(predicate1);
        FindOrderCommand command2 = new FindOrderCommand(predicate2);
        assertFalse(command1.equals(command2));
    }

    @Test
    public void toString_returnsCorrectFormat() {
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(createMap(
                OrderContainsKeywordsPredicate.SearchType.ITEM, "pizza"));
        FindOrderCommand command = new FindOrderCommand(predicate);
        String expected = FindOrderCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }
}
