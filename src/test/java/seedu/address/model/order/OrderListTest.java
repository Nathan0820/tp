package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.ORDER_A;
import static seedu.address.testutil.TypicalOrders.ORDER_B;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.order.exceptions.OrderNotFoundException;
import seedu.address.testutil.OrderBuilder;

public class OrderListTest {

    private final OrderList orderList = new OrderList();

    @Test
    public void add_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.add(null));
    }

    @Test
    public void add_validOrder_success() {
        orderList.add(ORDER_A);
        assertEquals(Collections.singletonList(ORDER_A), orderList.asUnmodifiableObservableList());
    }

    @Test
    public void remove_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.remove(null));
    }

    @Test
    public void remove_orderNotInList_throwsOrderNotFoundException() {
        assertThrows(OrderNotFoundException.class, () -> orderList.remove(ORDER_A));
    }

    @Test
    public void remove_existingOrder_success() {
        orderList.add(ORDER_A);
        orderList.remove(ORDER_A);
        assertTrue(orderList.asUnmodifiableObservableList().isEmpty());
    }

    @Test
    public void removeOrdersForCustomer_removesAllMatching() {
        Order order1 = new OrderBuilder().withCustomerIndex(1).build();
        Order order2 = new OrderBuilder().withCustomerIndex(1).build();
        Order order3 = new OrderBuilder().withCustomerIndex(2).build();

        orderList.setOrders(Arrays.asList(order1, order2, order3));
        orderList.removeOrdersForCustomer(Index.fromOneBased(1));

        assertEquals(Collections.singletonList(order3), orderList.asUnmodifiableObservableList());
    }

    @Test
    public void setOrders_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.setOrders((List<Order>) null));
    }

    @Test
    public void setOrders_list_replacesOwnList() {
        orderList.add(ORDER_A);
        List<Order> newList = Collections.singletonList(ORDER_B);

        orderList.setOrders(newList);

        OrderList expected = new OrderList();
        expected.add(ORDER_B);

        assertEquals(expected, orderList);
    }

    @Test
    public void asUnmodifiableObservableList_modify_throwsUnsupportedOperationException() {
        orderList.add(ORDER_A);
        assertThrows(UnsupportedOperationException.class, () ->
                orderList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals() {
        OrderList list1 = new OrderList();
        OrderList list2 = new OrderList();

        // both empty
        assertTrue(list1.equals(list2));

        // same contents
        list1.add(ORDER_A);
        list2.add(ORDER_A);
        assertTrue(list1.equals(list2));

        // same object
        assertTrue(list1.equals(list1));

        // null
        assertFalse(list1.equals(null));

        // different type
        assertFalse(list1.equals(5));

        // different contents
        list2.add(ORDER_B);
        assertFalse(list1.equals(list2));
    }

    @Test
    public void toStringMethod() {
        assertEquals(orderList.asUnmodifiableObservableList().toString(), orderList.toString());
    }
}
