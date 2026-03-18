package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
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
            .withDeliveryTime("2030-12-01 1800")
            .withAddress(VALID_ADDRESS_AMY)
            .withStatus("PREPARING")
            .build();

    public static final Order ORDER_B = new OrderBuilder()
            .withCustomerIndex(2)
            .withItem("Burger")
            .withQuantity("1")
            .withDeliveryTime("2030-12-02 1230")
            .withAddress(VALID_ADDRESS_BOB)
            .withStatus("PREPARING")
            .build();

    public static final Order ORDER_C = new OrderBuilder()
            .withCustomerIndex(1)
            .withItem("Sushi")
            .withQuantity("3")
            .withDeliveryTime("2030-12-03 2000")
            .withAddress(VALID_ADDRESS_AMY)
            .withStatus("DELIVERED")
            .build();

    public static final Order ORDER_D = new OrderBuilder()
            .withCustomerIndex(2)
            .withItem("Cake")
            .withQuantity("2")
            .withDeliveryTime("2030-12-15 1430")
            .withAddress(VALID_ADDRESS_AMY)
            .withStatus("READY")
            .build();
    private TypicalOrders() {} // prevents instantiation

    /**
     * Returns an {@code OrderList} with all the typical orders.
     */
    public static AddressBook getTypicalAddressbook() {
        AddressBook ab = new AddressBook();
        for (Order order : getTypicalOrders()) {
            ab.addOrder(order);
        }
        return ab;
    }

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(ORDER_A, ORDER_B, ORDER_C));
    }
}

