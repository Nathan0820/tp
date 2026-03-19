package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Order;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;

public class OrderListPanelTest {

    @Test
    public void getOrderComparator_earlierDateBeforeLaterDate() {
        Order earlierOrder = createOrder("2030-12-01 1800");
        Order laterOrder = createOrder("2030-12-03 2000");

        int result = OrderListPanel.getOrderComparator().compare(earlierOrder, laterOrder);

        assertTrue(result > 0, "Earlier date should come after later date (descending order)");
    }

    @Test
    public void getOrderComparator_laterDateBeforeEarlierDate() {
        Order earlierOrder = createOrder("2030-12-01 1800");
        Order laterOrder = createOrder("2030-12-03 2000");

        int result = OrderListPanel.getOrderComparator().compare(laterOrder, earlierOrder);

        assertTrue(result < 0, "Later date should come before earlier date (descending order)");
    }

    @Test
    public void getOrderComparator_sameDate_returnsZero() {
        Order order1 = createOrder("2030-12-01 1800");
        Order order2 = createOrder("2030-12-01 1800");

        int result = OrderListPanel.getOrderComparator().compare(order1, order2);

        assertEquals(0, result, "Same date should return 0");
    }

    @Test
    public void getOrderComparator_descendingOrderWithMultipleOrders() {
        Order order1 = createOrder("2030-12-01 1800"); // Earliest
        Order order2 = createOrder("2030-12-02 1200");
        Order order3 = createOrder("2030-12-03 2000"); // Latest

        var comparator = OrderListPanel.getOrderComparator();

        assertTrue(comparator.compare(order3, order2) < 0);
        assertTrue(comparator.compare(order2, order1) < 0);
        assertTrue(comparator.compare(order3, order1) < 0);
    }

    @Test
    public void getOrderComparator_handlesDifferentTimesOnSameDay() {
        Order morningOrder = createOrder("2030-12-01 0800");
        Order eveningOrder = createOrder("2030-12-01 2000");

        int result = OrderListPanel.getOrderComparator().compare(eveningOrder, morningOrder);

        assertTrue(result < 0, "Evening time should come before morning time (descending)");
    }

    private Order createOrder(String deliveryTime) {
        return new Order(
                UUID.randomUUID(),
                new Item("Test Item"),
                new Quantity("1"),
                new DeliveryTime(deliveryTime),
                new Address("Test Address"),
                new Status("PREPARING")
        );
    }
}
