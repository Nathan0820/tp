package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names: empty or lacks alphanumeric characters
        assertFalse(Tag.isValidTagName(""));
        assertFalse(Tag.isValidTagName("   "));
        assertFalse(Tag.isValidTagName("---"));
        assertFalse(Tag.isValidTagName("___"));
        assertFalse(Tag.isValidTagName("-_-_-"));

        // invalid tag names: contains disallowed symbols
        assertFalse(Tag.isValidTagName("a@"));
        assertFalse(Tag.isValidTagName("no@nuts"));
        assertFalse(Tag.isValidTagName("VIP#1"));

        // valid tag names
        assertTrue(Tag.isValidTagName("a")); // single letter
        assertTrue(Tag.isValidTagName("8")); // single number
        assertTrue(Tag.isValidTagName("123")); // numbers only
        assertTrue(Tag.isValidTagName("no nuts")); // with internal space
        assertTrue(Tag.isValidTagName("VIP-monthly")); // with hyphen
        assertTrue(Tag.isValidTagName("promo_2026")); // with underscore
        assertTrue(Tag.isValidTagName("corporate - client")); // with hyphen and spaces
        assertTrue(Tag.isValidTagName("a-b_c 1")); // all allowed types together
    }
}
