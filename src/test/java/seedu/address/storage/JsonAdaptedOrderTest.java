package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Order;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedOrderTest {

    private static final String INVALID_ITEM = "Pizza*";
    private static final String INVALID_QUANTITY = "-5";
    private static final String INVALID_DELIVERY_TIME = "not-a-date";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_STATUS = "UNKNOWN";

    private static final String VALID_ITEM = "Pizza";
    private static final String VALID_QUANTITY = "3";
    private static final String VALID_DELIVERY_TIME = "2030-01-01 1800";
    private static final String VALID_ADDRESS = "123 Jurong West St 42";
    private static final String VALID_STATUS = "PREPARING";

    // A small list of persons to simulate the saved address book
    private final List<Person> validPersons = List.of(
            new PersonBuilder().withName("Alice").build(),
            new PersonBuilder().withName("Bob").build()
    );

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws Exception {
        // Build a person with a stable UUID
        Person customer = new PersonBuilder().withName("Alice").build();
        UUID customerId = customer.getId();

        Order order = new OrderBuilder()
                .withItem(VALID_ITEM)
                .withQuantity(VALID_QUANTITY)
                .withDeliveryTime(VALID_DELIVERY_TIME)
                .withAddress(VALID_ADDRESS)
                .withStatus(VALID_STATUS)
                .withCustomerId(customerId)
                .build();

        JsonAdaptedOrder jsonOrder = new JsonAdaptedOrder(order);
        assertEquals(order, jsonOrder.toModelType(validPersons));
    }

    @Test
    public void toModelType_nullItem_throwsIllegalValueException() {
        Person customer = new PersonBuilder().withName("Alice").build();
        String validCustomerId = customer.getId().toString();

        JsonAdaptedOrder order = new JsonAdaptedOrder(
                null, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, validCustomerId);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Item.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_invalidItem_throwsIllegalValueException() {
        Person customer = new PersonBuilder().withName("Alice").build();
        String validCustomerId = customer.getId().toString();

        JsonAdaptedOrder order = new JsonAdaptedOrder(
                INVALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, validCustomerId);
        assertThrows(IllegalValueException.class, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        Person customer = new PersonBuilder().withName("Alice").build();
        String validCustomerId = customer.getId().toString();

        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, null, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, validCustomerId);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        Person customer = new PersonBuilder().withName("Alice").build();
        String validCustomerId = customer.getId().toString();

        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, INVALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, validCustomerId);
        assertThrows(IllegalValueException.class, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_nullDeliveryTime_throwsIllegalValueException() {
        Person customer = new PersonBuilder().withName("Alice").build();
        String validCustomerId = customer.getId().toString();

        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, null, VALID_ADDRESS, VALID_STATUS, validCustomerId);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_invalidDeliveryTime_throwsIllegalValueException() {
        Person customer = new PersonBuilder().withName("Alice").build();
        String validCustomerId = customer.getId().toString();

        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, INVALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, validCustomerId);
        assertThrows(IllegalValueException.class, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        Person customer = new PersonBuilder().withName("Alice").build();
        String validCustomerId = customer.getId().toString();

        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, null, VALID_STATUS, validCustomerId);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        Person customer = new PersonBuilder().withName("Alice").build();
        String validCustomerId = customer.getId().toString();

        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, INVALID_ADDRESS, VALID_STATUS, validCustomerId);
        assertThrows(IllegalValueException.class, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        Person customer = new PersonBuilder().withName("Alice").build();
        String validCustomerId = customer.getId().toString();

        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, null, validCustomerId);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        Person customer = new PersonBuilder().withName("Alice").build();
        String validCustomerId = customer.getId().toString();

        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, INVALID_STATUS, validCustomerId);
        assertThrows(IllegalValueException.class, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_nullCustomerId_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Customer Id");
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_zeroCustomerIndex_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, "0");
        assertThrows(IllegalValueException.class, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_negativeCustomerIndex_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, "-1");
        assertThrows(IllegalValueException.class, () -> order.toModelType(validPersons));
    }

    @Test
    public void toModelType_customerIndexOutOfBounds_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, "999");
        assertThrows(IllegalValueException.class, () -> order.toModelType(validPersons));
    }
}
