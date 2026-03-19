package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * A utility class containing a typical {@code AddressBook} with persons and orders to be used in tests.
 */
public class TypicalAddressBook {

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();

        // Add persons
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }

        // Add orders
        for (Order order : TypicalOrders.getTypicalOrders()) {
            ab.addOrder(order);
        }

        return ab;
    }
}
