package seedu.address.model.order;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.exceptions.OrderNotFoundException;

/**
 * A list of orders that does not allow nulls.
 *
 * <p>Unlike {@code UniquePersonList}, this list does not enforce uniqueness between its
 * elements; multiple identical {@code Order} objects may exist unless the application
 * enforces uniqueness at a higher level.</p>
 *
 * <p>Removal of a single order is based on {@code Order#equals(Object)}, ensuring that
 * the order with exactly matching fields is removed. Bulk removal of orders associated
 * with a particular customer is supported via {@code removeOrdersForCustomer(Index)}.</p>
 *
 * <p>Supports a minimal set of list operations.</p>
 */
public class OrderList {
    private final ObservableList<Order> internalList = FXCollections.observableArrayList();
    private final ObservableList<Order> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds an order to the list.
     */
    public void add(Order order) {
        requireNonNull(order);
        internalList.add(order);
    }

    /**
     * Removes the equivalent order from the list.
     * The order must exist in the list.
     */
    public void remove(Order order) {
        requireNonNull(order);
        if (!internalList.remove(order)) {
            throw new OrderNotFoundException();
        }
    }

    /**
     * Removes all orders associated with the specified customer id.
     */
    public void removeOrdersForCustomer(UUID customerId) {
        requireNonNull(customerId);
        internalList.removeIf(order -> order.getCustomerId().equals(customerId));
    }

    /**
     * Replaces the contents of this list with {@code orders}.
     */
    public void setOrders(List<Order> orders) {
        internalList.setAll(orders);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Order> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    /**
     * Contains method for OrderList to check if an order exists
     */
    public boolean contains(Order order) {
        requireNonNull(order);
        return internalList.contains(order);
    }

    public int size() {
        return internalList.size();
    }

    public Order get(int index) {
        return internalList.get(index);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderList)) {
            return false;
        }

        OrderList otherOrderList = (OrderList) other;
        return internalList.equals(otherOrderList.internalList);
    }


    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}
