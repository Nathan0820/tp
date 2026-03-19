package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.ORDER_A;
import static seedu.address.testutil.TypicalOrders.ORDER_B;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.OrderBuilder;

public class OrderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Order(null, null, null, null, null, null));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Order orderCopy = new OrderBuilder(ORDER_A).build();
        assertTrue(ORDER_A.equals(orderCopy));

        // same object -> returns true
        assertTrue(ORDER_A.equals(ORDER_A));

        // null -> returns false
        assertFalse(ORDER_A.equals(null));

        // different type -> returns false
        assertFalse(ORDER_A.equals(5));

        // different order -> returns false
        assertFalse(ORDER_A.equals(ORDER_B));

        // different item -> returns false
        Order editedOrder = new OrderBuilder(ORDER_A).withItem("Different Item").build();
        assertFalse(ORDER_A.equals(editedOrder));

        // different quantity -> returns false
        editedOrder = new OrderBuilder(ORDER_A).withQuantity("99").build();
        assertFalse(ORDER_A.equals(editedOrder));

        // different delivery time -> returns false
        editedOrder = new OrderBuilder(ORDER_A).withDeliveryTime("2030-01-01 2359").build();
        assertFalse(ORDER_A.equals(editedOrder));

        // different address -> returns false
        editedOrder = new OrderBuilder(ORDER_A).withAddress("999 New Road").build();
        assertFalse(ORDER_A.equals(editedOrder));

        // different status -> returns false
        editedOrder = new OrderBuilder(ORDER_A).withStatus("DELIVERED").build();
        assertFalse(ORDER_A.equals(editedOrder));

        // different customerId -> returns false
        UUID differentCustomerId = UUID.randomUUID();
        editedOrder = new OrderBuilder(ORDER_A).withCustomerId(differentCustomerId).build();
        assertFalse(ORDER_A.equals(editedOrder));
    }

    @Test
    public void toStringMethod() {
        String expected = Order.class.getCanonicalName()
                + "{item=" + ORDER_A.getItem()
                + ", quantity=" + ORDER_A.getQuantity()
                + ", deliveryTime=" + ORDER_A.getDeliveryTime()
                + ", address=" + ORDER_A.getAddress()
                + ", status=" + ORDER_A.getStatus() + "}";
        assertEquals(expected, ORDER_A.toString());
    }
}
