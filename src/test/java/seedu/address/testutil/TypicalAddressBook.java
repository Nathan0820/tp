package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

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
