package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderList;
import seedu.address.model.order.Status;
import seedu.address.testutil.OrderBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.TypicalOrders.ORDER_A;
import static seedu.address.testutil.TypicalOrders.ORDER_B;
import static seedu.address.testutil.TypicalOrders.ORDER_C;
import static seedu.address.testutil.TypicalOrders.ORDER_D;

/**
 * Test cases for AddressBook order retrieval methods
 */
public class ViewOrderCommandTest {

    private AddressBook addressBook;
    private Order order1;
    private Order order2;
    private Order order3;
    private Order order4;

    @BeforeEach
    public void setUp() {
        addressBook = new AddressBook();

        // Create test persons

        // Create test orders
        order1 = new OrderBuilder(ORDER_A).build();
        order2 = new OrderBuilder(ORDER_B).build();
        order3 = new OrderBuilder(ORDER_C).build();
        order4 = new OrderBuilder(ORDER_D).build();

    }

    // ===== Tests for getOrdersByStatus() =====

    @Test
    public void getOrdersByStatus_withPreparingOrders_returnsPreparingOrders() {
        addressBook.addOrder(order1); // PREPARING
        addressBook.addOrder(order3); // READY
        addressBook.addOrder(order2); // PREPARING


        OrderList result = addressBook.getOrdersByStatus(new Status("PREPARING"));


        assertEquals(2, result.size());
        assertTrue(result.contains(order1));
        assertTrue(result.contains(order2));

    }

    @Test
    public void getOrdersByStatus_withReadyOrders_returnsReadyOrders() {
        addressBook.addOrder(order1); // PREPARING
        addressBook.addOrder(order4); // READY
        addressBook.addOrder(order2); // DELIVERED

        OrderList result = addressBook.getOrdersByStatus(new Status("READY"));

        assertEquals(1, result.size());
        assertTrue(result.contains(order4));
    }

    @Test
    public void getOrdersByStatus_withDeliveredOrders_returnsDeliveredOrders() {
        addressBook.addOrder(order1); // PREPARING
        addressBook.addOrder(order3); // DELIVERED

        OrderList result = addressBook.getOrdersByStatus(new Status("DELIVERED"));

        assertEquals(1, result.size());
        assertTrue(result.contains(order3));
    }

    @Test
    public void getOrdersByStatus_noMatchingOrders_returnsEmptyList() {
        addressBook.addOrder(order1); // PREPARING
        addressBook.addOrder(order2); // READY

        OrderList result = addressBook.getOrdersByStatus(new Status("CANCELLED"));

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    public void getOrdersByStatus_withNullOrderList_skipsPersonWithoutOrders() {
        // person1 has null orders (not initialized)
        // person2 has orders
        addressBook.addOrder(order2); // PREPARING

        OrderList result = addressBook.getOrdersByStatus(new Status("PREPARING"));

        assertEquals(1, result.size());
        assertTrue(result.contains(order2));
    }


    // ===== Tests for getAllOrders() =====

    @Test
    public void getAllOrders_withMultipleOrders_returnsAllOrders() {
        addressBook.addOrder(order1);
        addressBook.addOrder(order3);
        addressBook.addOrder(order2);
        addressBook.addOrder(order4);

        OrderList result = addressBook.getAllOrders();

        assertEquals(4, result.size());
        assertTrue(result.contains(order1));
        assertTrue(result.contains(order2));
        assertTrue(result.contains(order3));
        assertTrue(result.contains(order4));
    }

    @Test
    public void getAllOrders_noOrders_returnsEmptyList() {
        OrderList result = addressBook.getAllOrders();

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    public void getAllOrders_withNullOrderLists_skipsPersonsWithoutOrders() {
        // person1 has null orders
        // person2 has orders
        addressBook.addOrder(order4);
        addressBook.addOrder(order2);
        // person3 has null orders

        OrderList result = addressBook.getAllOrders();

        assertEquals(2, result.size());
        assertTrue(result.contains(order4));
        assertTrue(result.contains(order2));
    }

    @Test
    public void getAllOrders_allPersonsHaveNullOrders_returnsEmptyList() {
        // All persons have null orders (default state)
        OrderList result = addressBook.getAllOrders();

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }
}


