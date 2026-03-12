package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class QuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "0"; // zero is not allowed
        assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidQuantity));
    }

    @Test
    public void isValidQuantity() {
        // null -> throws
        assertThrows(NullPointerException.class, () -> Quantity.isValidQuantity(null));

        // invalid quantities
        assertFalse(Quantity.isValidQuantity(""));      // empty
        assertFalse(Quantity.isValidQuantity(" "));     // spaces
        assertFalse(Quantity.isValidQuantity("0"));     // zero not allowed
        assertFalse(Quantity.isValidQuantity("-1"));    // negative
        assertFalse(Quantity.isValidQuantity("1.5"));   // decimal
        assertFalse(Quantity.isValidQuantity("abc"));   // non-numeric
        assertFalse(Quantity.isValidQuantity("2a"));    // alphanumeric

        // valid quantities
        assertTrue(Quantity.isValidQuantity("1"));
        assertTrue(Quantity.isValidQuantity("5"));
        assertTrue(Quantity.isValidQuantity("123"));
    }

    @Test
    public void equals() {
        Quantity q = new Quantity("3");

        // same values -> true
        assertTrue(q.equals(new Quantity("3")));

        // same object -> true
        assertTrue(q.equals(q));

        // null -> false
        assertFalse(q.equals(null));

        // different type -> false
        assertFalse(q.equals(5));

        // different value -> false
        assertFalse(q.equals(new Quantity("10")));
    }
}

