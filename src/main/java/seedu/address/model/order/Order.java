package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Address;

/**
 * Represents an Order in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {
    // Data fields
    private final Item item;
    private final Quantity quantity;
    private final DeliveryTime deliveryTime;
    private final Address address;
    private final Status status;

    /**
     * Every field must be present and not null.
     */
    public Order(Item item, Quantity quantity, DeliveryTime deliveryTime, Address address, Status status) {
        requireAllNonNull(item, quantity, deliveryTime, address, status);
        this.item = item;
        this.quantity = quantity;
        this.deliveryTime = deliveryTime;
        this.address = address;
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public DeliveryTime getDeliveryTime() {
        return deliveryTime;
    }

    public Address getAddress() {
        return address;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns true if both orders have the same item, quantity, and delivery time.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return item.equals(otherOrder.item)
                && quantity.equals(otherOrder.quantity)
                && deliveryTime.equals(otherOrder.deliveryTime);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(item, quantity, deliveryTime, address, status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("item", item)
                .add("quantity", quantity)
                .add("deliveryTime", deliveryTime)
                .add("address", address)
                .add("status", status)
                .toString();
    }
}
