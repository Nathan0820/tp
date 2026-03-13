package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
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
    private static final Integer VALID_CUSTOMER_INDEX = 1;

    // A small list of persons to simulate the saved address book
    private final List<Person> VALID_PERSONS = List.of(
            new PersonBuilder().withName("Alice").build(),
            new PersonBuilder().withName("Bob").build()
    );

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws Exception {
        Order order = new OrderBuilder()
                .withItem(VALID_ITEM)
                .withQuantity(VALID_QUANTITY)
                .withDeliveryTime(VALID_DELIVERY_TIME)
                .withAddress(VALID_ADDRESS)
                .withStatus(VALID_STATUS)
                .withCustomerIndex(Index.fromOneBased(VALID_CUSTOMER_INDEX))
                .build();

        JsonAdaptedOrder jsonOrder = new JsonAdaptedOrder(order);
        assertEquals(order, jsonOrder.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_nullItem_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                null, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, VALID_CUSTOMER_INDEX);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Item.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_invalidItem_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                INVALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, VALID_CUSTOMER_INDEX);
        assertThrows(IllegalValueException.class, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, null, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, VALID_CUSTOMER_INDEX);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, INVALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, VALID_CUSTOMER_INDEX);
        assertThrows(IllegalValueException.class, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_nullDeliveryTime_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, null, VALID_ADDRESS, VALID_STATUS, VALID_CUSTOMER_INDEX);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_invalidDeliveryTime_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, INVALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, VALID_CUSTOMER_INDEX);
        assertThrows(IllegalValueException.class, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, null, VALID_STATUS, VALID_CUSTOMER_INDEX);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, INVALID_ADDRESS, VALID_STATUS, VALID_CUSTOMER_INDEX);
        assertThrows(IllegalValueException.class, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, null, VALID_CUSTOMER_INDEX);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, INVALID_STATUS, VALID_CUSTOMER_INDEX);
        assertThrows(IllegalValueException.class, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_nullCustomerIndex_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Customer Index");
        assertThrows(IllegalValueException.class, expectedMessage, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_zeroCustomerIndex_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, 0);
        assertThrows(IllegalValueException.class, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_negativeCustomerIndex_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, -1);
        assertThrows(IllegalValueException.class, () -> order.toModelType(VALID_PERSONS));
    }

    @Test
    public void toModelType_customerIndexOutOfBounds_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                VALID_ITEM, VALID_QUANTITY, VALID_DELIVERY_TIME, VALID_ADDRESS, VALID_STATUS, 999);
        assertThrows(IllegalValueException.class, () -> order.toModelType(VALID_PERSONS));
    }
}
