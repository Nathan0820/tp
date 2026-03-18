package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Order;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;
import seedu.address.testutil.PersonBuilder;

public class OrderCardTest {

    private ObservableList<seedu.address.model.person.Person> personList;

    @BeforeEach
    public void setUp() {
        seedu.address.model.person.Person person1 = new PersonBuilder()
                .withName("Alice Pauline")
                .withPhone("94351253")
                .build();
        seedu.address.model.person.Person person2 = new PersonBuilder()
                .withName("Benson Meier")
                .withPhone("98765432")
                .build();

        personList = FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(person1, person2)));
    }

    @Test
    public void getCustomerName_validFirstIndex_returnsCorrectName() {
        Order order = new Order(
                Index.fromZeroBased(0),
                new Item("Pizza"),
                new Quantity("2"),
                new DeliveryTime("2030-12-01 1800"),
                new Address("123 Main Street"),
                new Status("PREPARING")
        );

        String result = OrderCard.getCustomerName(order, personList);

        assertEquals("Alice Pauline", result);
    }

    @Test
    public void getCustomerName_validSecondIndex_returnsCorrectName() {
        Order order = new Order(
                Index.fromZeroBased(1),
                new Item("Burger"),
                new Quantity("1"),
                new DeliveryTime("2030-12-02 1230"),
                new Address("456 Side Street"),
                new Status("READY")
        );

        String result = OrderCard.getCustomerName(order, personList);

        assertEquals("Benson Meier", result);
    }

    @Test
    public void getCustomerName_invalidIndex_returnsUnknownCustomer() {
        Order order = new Order(
                Index.fromZeroBased(99),
                new Item("Sushi"),
                new Quantity("3"),
                new DeliveryTime("2030-12-03 2000"),
                new Address("789 Other Street"),
                new Status("DELIVERED")
        );

        String result = OrderCard.getCustomerName(order, personList);

        assertEquals("Unknown Customer", result);
    }

    @Test
    public void getCustomerName_negativeIndex_returnsUnknownCustomer() {
        Order order = new Order(
                Index.fromZeroBased(-1),
                new Item("Cake"),
                new Quantity("1"),
                new DeliveryTime("2030-12-04 1500"),
                new Address("101 Test Street"),
                new Status("PREPARING")
        );

        String result = OrderCard.getCustomerName(order, personList);

        assertEquals("Unknown Customer", result);
    }

    @Test
    public void getCustomerName_emptyPersonList_returnsUnknownCustomer() {
        ObservableList<seedu.address.model.person.Person> emptyList =
                FXCollections.observableArrayList(new ArrayList<>());

        Order order = new Order(
                Index.fromZeroBased(0),
                new Item("Pizza"),
                new Quantity("2"),
                new DeliveryTime("2030-12-01 1800"),
                new Address("123 Main Street"),
                new Status("PREPARING")
        );

        String result = OrderCard.getCustomerName(order, emptyList);

        assertEquals("Unknown Customer", result);
    }
}
