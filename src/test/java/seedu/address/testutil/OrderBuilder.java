package seedu.address.testutil;

import java.util.UUID;

import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Order;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {

    public static final int DEFAULT_CUSTOMER_INDEX = 1;
    public static final String DEFAULT_ITEM = "Pizza";
    public static final String DEFAULT_QUANTITY = "1";
    public static final String DEFAULT_DELIVERY_TIME = "2030-12-01 1800";
    public static final String DEFAULT_ADDRESS = "123 Default Street";
    public static final String DEFAULT_STATUS = "PREPARING";

    private UUID customerId;
    private Item item;
    private Quantity quantity;
    private DeliveryTime deliveryTime;
    private Address address;
    private Status status;

    /**
     * Creates a {@code OrderBuilder} with the default details.
     */
    public OrderBuilder() {
        customerId = UUID.randomUUID();
        item = new Item(DEFAULT_ITEM);
        quantity = new Quantity(DEFAULT_QUANTITY);
        deliveryTime = new DeliveryTime(DEFAULT_DELIVERY_TIME);
        address = new Address(DEFAULT_ADDRESS);
        status = new Status(DEFAULT_STATUS);
    }

    /**
     * Initializes the OrderBuilder with the data of {@code orderToCopy}.
     */
    public OrderBuilder(Order orderToCopy) {
        customerId = orderToCopy.getCustomerId();
        item = orderToCopy.getItem();
        quantity = orderToCopy.getQuantity();
        deliveryTime = orderToCopy.getDeliveryTime();
        address = orderToCopy.getAddress();
        status = orderToCopy.getStatus();
    }

    /**
     * Sets the customer ID of the {@code Order} being built.
     *
     * @param id The customer ID.
     * @return This {@code OrderBuilder} for method chaining.
     */
    public OrderBuilder withCustomerId(UUID id) {
        this.customerId = id;
        return this;
    }

    /**
     * Sets the item of the {@code Order} being built.
     *
     * @param item The item name.
     * @return This {@code OrderBuilder} for method chaining.
     */
    public OrderBuilder withItem(String item) {
        this.item = new Item(item);
        return this;
    }

    /**
     * Sets the quantity of the {@code Order} being built.
     *
     * @param quantity The quantity value.
     * @return This {@code OrderBuilder} for method chaining.
     */
    public OrderBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the delivery time of the {@code Order} being built.
     *
     * @param time The delivery time in {@code yyyy-MM-dd HHmm} format.
     * @return This {@code OrderBuilder} for method chaining.
     */
    public OrderBuilder withDeliveryTime(String time) {
        this.deliveryTime = new DeliveryTime(time);
        return this;
    }

    /**
     * Sets the address of the {@code Order} being built.
     *
     * @param address The delivery address.
     * @return This {@code OrderBuilder} for method chaining.
     */
    public OrderBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the status of the {@code Order} being built.
     *
     * @param status The order status.
     * @return This {@code OrderBuilder} for method chaining.
     */
    public OrderBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    public Order build() {
        return new Order(customerId, item, quantity, deliveryTime, address, status);
    }
}
