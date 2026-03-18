package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.UUID;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderList;
import seedu.address.model.order.Status;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final OrderList orders;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        orders = new OrderList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setOrder(newData.getOrderList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }
    public void setOrder(List<Order> orders) {
        this.orders.setOrders(orders);
    }
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// order-level operations

    /**
     * Adds an order to the address book.
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Removes {@code order} from this {@code AddressBook}.
     * {@code order} must exist in the address book.
     */
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    /**
     * Removes all orders in this {@code AddressBook} made by customer
     * identified by {@code customerId}.
     */
    public void removeOrdersForCustomer(UUID customerId) {
        orders.removeOrdersForCustomer(customerId);
    }

    /**
     * Gets all orders by with a specific status{@code status} across all customers
     */
    public OrderList getOrdersByStatus(Status status) {
        OrderList filteredOrders = new OrderList();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getStatus().equals(status)) {
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }

    /**
     * Gets all order across all customers
     */
    public OrderList getAllOrders() {
        OrderList allOrders = new OrderList();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            allOrders.add(order);
        }
        return allOrders;
    }
    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Order> getOrderList() {
        return orders.asUnmodifiableObservableList();
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
