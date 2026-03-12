package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.order.Order;
import seedu.address.model.order.OrderList;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {

    public static final Order ORDER_A = new OrderBuilder()
            .withCustomerIndex(1)
            .withItem("Pizza")
            .withQuantity("2")
            .withDeliveryTime("18:00")
            .withAddress(VALID_ADDRESS_AMY)
            .withStatus("Pending")
            .build();

    public static final Order ORDER_B = new OrderBuilder()
            .withCustomerIndex(2)
            .withItem("Burger")
            .withQuantity("1")
            .withDeliveryTime("12:30")
            .withAddress(VALID_ADDRESS_BOB)
            .withStatus("Pending")
            .build();

    public static final Order ORDER_C = new OrderBuilder()
            .withCustomerIndex(1)
            .withItem("Sushi")
            .withQuantity("3")
            .withDeliveryTime("20:00")
            .withAddress(VALID_ADDRESS_AMY)
            .withStatus("Delivered")
            .build();

    private TypicalOrders() {} // prevents instantiation

    /**
     * Returns an {@code OrderList} with all the typical orders.
     */
    public static OrderList getTypicalOrderList() {
        OrderList list = new OrderList();
        for (Order order : getTypicalOrders()) {
            list.add(order);
        }
        return list;
    }

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(ORDER_A, ORDER_B, ORDER_C));
    }
}

