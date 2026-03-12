package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
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
    public static final String DEFAULT_DELIVERY_TIME = "18:00";
    public static final String DEFAULT_ADDRESS = "123 Default Street";
    public static final String DEFAULT_STATUS = "Pending";

    private Index customerIndex;
    private Item item;
    private Quantity quantity;
    private DeliveryTime deliveryTime;
    private Address address;
    private Status status;

    /**
     * Creates a {@code OrderBuilder} with the default details.
     */
    public OrderBuilder() {
        customerIndex = Index.fromOneBased(DEFAULT_CUSTOMER_INDEX);
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
        customerIndex = orderToCopy.getCustomerIndex();
        item = orderToCopy.getItem();
        quantity = orderToCopy.getQuantity();
        deliveryTime = orderToCopy.getDeliveryTime();
        address = orderToCopy.getAddress();
        status = orderToCopy.getStatus();
    }

    public OrderBuilder withCustomerIndex(int index) {
        this.customerIndex = Index.fromOneBased(index);
        return this;
    }

    public OrderBuilder withItem(String item) {
        this.item = new Item(item);
        return this;
    }

    public OrderBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    public OrderBuilder withDeliveryTime(String time) {
        this.deliveryTime = new DeliveryTime(time);
        return this;
    }

    public OrderBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    public OrderBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    public Order build() {
        return new Order(customerIndex, item, quantity, deliveryTime, address, status);
    }
}
