package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ItemTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Item(null));
    }

    @Test
    public void constructor_invalidItem_throwsIllegalArgumentException() {
        String invalidItem = "";
        assertThrows(IllegalArgumentException.class, () -> new Item(invalidItem));
    }

    @Test
    public void isValidItem() {
        // null item
        assertThrows(NullPointerException.class, () -> Item.isValidItem(null));

        // invalid item
        assertFalse(Item.isValidItem(""));        // empty string
        assertFalse(Item.isValidItem(" "));       // spaces only
        assertFalse(Item.isValidItem("^"));       // non-alphanumeric only
        assertFalse(Item.isValidItem("pizza*"));  // contains invalid characters

        // valid item
        assertTrue(Item.isValidItem("Pizza"));          // alphabets only
        assertTrue(Item.isValidItem("12345"));          // numbers only
        assertTrue(Item.isValidItem("Pizza 2"));        // alphanumeric with space
        assertTrue(Item.isValidItem("Chicken Rice"));   // multiple words
        assertTrue(Item.isValidItem("Big Mac 2"));      // long names
    }

    @Test
    public void equals() {
        Item item = new Item("Pizza");

        // same values -> returns true
        assertTrue(item.equals(new Item("Pizza")));

        // same object -> returns true
        assertTrue(item.equals(item));

        // null -> returns false
        assertFalse(item.equals(null));

        // different type -> returns false
        assertFalse(item.equals(5.0f));

        // different values -> returns false
        assertFalse(item.equals(new Item("Burger")));
    }
}

