package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.order.Order;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {

    public static final Order ORDER_A = new OrderBuilder()
            .withCustomerId(ALICE.getId())
            .withItem("Pizza")
            .withQuantity("2")
            .withDeliveryTime("2030-12-01 1800")
            .withAddress(VALID_ADDRESS_AMY)
            .withStatus("PREPARING")
            .build();

    public static final Order ORDER_B = new OrderBuilder()
            .withCustomerId(BOB.getId())
            .withItem("Burger")
            .withQuantity("1")
            .withDeliveryTime("2030-12-02 1230")
            .withAddress(VALID_ADDRESS_BOB)
            .withStatus("PREPARING")
            .build();

    public static final Order ORDER_C = new OrderBuilder()
            .withCustomerId(ALICE.getId())
            .withItem("Sushi")
            .withQuantity("3")
            .withDeliveryTime("2030-12-03 2000")
            .withAddress(VALID_ADDRESS_AMY)
            .withStatus("DELIVERED")
            .build();

    public static final Order ORDER_D = new OrderBuilder()
            .withCustomerId(BOB.getId())
            .withItem("Cake")
            .withQuantity("2")
            .withDeliveryTime("2030-12-15 1430")
            .withAddress(VALID_ADDRESS_AMY)
            .withStatus("READY")
            .build();

    private TypicalOrders() {} // prevents instantiation

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(ORDER_A, ORDER_B, ORDER_C));
    }
}

