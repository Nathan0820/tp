package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddOrderCommandTest {

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddOrderCommand(
                        null,
                        new OrderBuilder().build().getItem(),
                        new OrderBuilder().build().getQuantity(),
                        new OrderBuilder().build().getDeliveryTime(),
                        Optional.empty(),
                        Optional.empty()
                ));
    }

    @Test
    public void execute_orderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();

        // Add ALICE as the only customer
        modelStub.persons.add(ALICE);

        // Build an order for ALICE using her UUID
        Order expectedOrder = new OrderBuilder()
                .withCustomerId(ALICE.getId())
                .build();

        // Command still takes an index (1)
        AddOrderCommand command = new AddOrderCommand(
                Index.fromOneBased(1),
                expectedOrder.getItem(),
                expectedOrder.getQuantity(),
                expectedOrder.getDeliveryTime(),
                Optional.of(expectedOrder.getAddress()),
                Optional.of(expectedOrder.getStatus())
        );

        CommandResult result = command.execute(modelStub);

        assertEquals(
                String.format(AddOrderCommand.MESSAGE_SUCCESS,
                        Messages.format(expectedOrder, ALICE.getName().fullName)),
                result.getFeedbackToUser()
        );

        assertEquals(List.of(expectedOrder), modelStub.ordersAdded);
    }

    @Test
    public void execute_pastDeliveryTime_showsWarning() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        modelStub.persons.add(ALICE);

        Order expectedOrder = new OrderBuilder()
                .withCustomerId(ALICE.getId())
                .withDeliveryTime("2000-01-01 1200")
                .build();

        AddOrderCommand command = new AddOrderCommand(
                Index.fromOneBased(1),
                expectedOrder.getItem(),
                expectedOrder.getQuantity(),
                expectedOrder.getDeliveryTime(),
                Optional.of(expectedOrder.getAddress()),
                Optional.of(expectedOrder.getStatus())
        );

        CommandResult result = command.execute(modelStub);

        assertTrue(result.getFeedbackToUser().contains(AddOrderCommand.MESSAGE_PAST_TIME));
    }

    @Test
    public void execute_largeQuantity_showsWarning() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        modelStub.persons.add(ALICE);

        // Build an order with a large quantity (>= 500)
        Order expectedOrder = new OrderBuilder()
                .withCustomerId(ALICE.getId())
                .withQuantity("500")
                .build();

        AddOrderCommand command = new AddOrderCommand(
                Index.fromOneBased(1),
                expectedOrder.getItem(),
                expectedOrder.getQuantity(),
                expectedOrder.getDeliveryTime(),
                Optional.of(expectedOrder.getAddress()),
                Optional.of(expectedOrder.getStatus())
        );

        CommandResult result = command.execute(modelStub);

        assertTrue(result.getFeedbackToUser().contains(AddOrderCommand.MESSAGE_LARGE_QUANTITY_WARNING));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubWithPersons modelStub = new ModelStubWithPersons(List.of(ALICE));

        AddOrderCommand command = new AddOrderCommand(
                Index.fromOneBased(2), // invalid index
                new OrderBuilder().build().getItem(),
                new OrderBuilder().build().getQuantity(),
                new OrderBuilder().build().getDeliveryTime(),
                Optional.empty(),
                Optional.empty()
        );

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        // Create two persons with stable UUIDs
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();

        UUID aliceId = alice.getId();
        UUID bobId = bob.getId();

        // Build orders linked to those UUIDs
        Order orderA = new OrderBuilder().withCustomerId(aliceId).build();
        Order orderB = new OrderBuilder().withCustomerId(bobId).build();

        // Commands still take INDEX, not UUID
        AddOrderCommand cmdA1 = new AddOrderCommand(
                Index.fromOneBased(1),
                orderA.getItem(),
                orderA.getQuantity(),
                orderA.getDeliveryTime(),
                Optional.of(orderA.getAddress()),
                Optional.of(orderA.getStatus())
        );

        AddOrderCommand cmdA2 = new AddOrderCommand(
                Index.fromOneBased(1),
                orderA.getItem(),
                orderA.getQuantity(),
                orderA.getDeliveryTime(),
                Optional.of(orderA.getAddress()),
                Optional.of(orderA.getStatus())
        );

        AddOrderCommand cmdB = new AddOrderCommand(
                Index.fromOneBased(2),
                orderB.getItem(),
                orderB.getQuantity(),
                orderB.getDeliveryTime(),
                Optional.of(orderB.getAddress()),
                Optional.of(orderB.getStatus())
        );

        assertTrue(cmdA1.equals(cmdA1)); // same object
        assertTrue(cmdA1.equals(cmdA2)); // same values
        assertFalse(cmdA1.equals(cmdB)); // different index
        assertFalse(cmdA1.equals(null)); // null
        assertFalse(cmdA1.equals(5)); // different type
    }

    @Test
    public void toStringMethod() {
        // Build a person so we can get a valid UUID
        Person alice = new PersonBuilder().withName("Alice").build();

        // Build an order linked to Alice's UUID
        Order order = new OrderBuilder()
                .withCustomerId(alice.getId())
                .build();

        // Commands still take an index, not a UUID
        AddOrderCommand command = new AddOrderCommand(
                Index.fromOneBased(1),
                order.getItem(),
                order.getQuantity(),
                order.getDeliveryTime(),
                Optional.of(order.getAddress()),
                Optional.of(order.getStatus())
        );

        String expected = AddOrderCommand.class.getCanonicalName()
                + "{toAdd=null}";

        assertEquals(expected, command.toString());
    }

    // ============================================================
    //                          MODEL STUBS
    // ============================================================

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError();
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError();
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError();
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError();
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError();
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError();
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError();
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError();
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError();
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError();
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError();
        }

        @Override
        public Person findPersonById(UUID id) {
            throw new AssertionError();
        }

        @Override
        public void addOrder(Order order) {
            throw new AssertionError();
        }

        @Override
        public void setOrder(Order target, Order editedOrder) {
            throw new AssertionError();
        }

        @Override
        public void deleteOrder(Order order) {
            throw new AssertionError();
        }

        @Override
        public void deleteOrdersForCustomer(UUID id) {
            throw new AssertionError();
        }

        @Override
        public void updateFilteredOrderList(Predicate<Order> predicate) {
            throw new AssertionError();
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            throw new AssertionError();
        }

    }

    private class ModelStubAcceptingOrderAdded extends ModelStub {
        final List<Person> persons = new ArrayList<>();
        final List<Order> ordersAdded = new ArrayList<>();

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableList(persons);
        }

        @Override
        public void addOrder(Order order) {
            ordersAdded.add(order);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    private class ModelStubWithPersons extends ModelStub {
        private final List<Person> persons;

        ModelStubWithPersons(List<Person> persons) {
            this.persons = persons;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableList(persons);
        }
    }
}
