package seedu.address.ui;

import java.util.UUID;

import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Order;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;

public class OrderListPanelTest {
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
