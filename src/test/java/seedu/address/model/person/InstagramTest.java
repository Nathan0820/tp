package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class InstagramTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Instagram(null));
    }

    @Test
    public void constructor_invalidInstagram_throwsIllegalArgumentException() {
        String invalidInstagram = "";
        assertThrows(IllegalArgumentException.class, () -> new Instagram(invalidInstagram));
    }

    @Test
    public void constructor_validInstagramWithAtPrefix_stripsAtForStorage() {
        Instagram instagram = new Instagram("@sarah_eats");

        assertEquals("sarah_eats", instagram.toString());
    }

    @Test
    public void getDisplayValue_returnsAtPrefixedHandle() {
        Instagram instagram = new Instagram("sarah_eats");
        assertEquals("@sarah_eats", instagram.getDisplayValue());
    }

    @Test
    public void isValidInstagram() {
        // null instagram
        assertThrows(NullPointerException.class, () -> Instagram.isValidInstagram(null));

        // invalid instagram handles
        assertFalse(Instagram.isValidInstagram("")); // empty string
        assertFalse(Instagram.isValidInstagram(" ")); // spaces only
        assertFalse(Instagram.isValidInstagram(".startswithdot")); // starts with period
        assertFalse(Instagram.isValidInstagram("endswithdot.")); // ends with period
        assertFalse(Instagram.isValidInstagram("double..dot")); // consecutive periods
        assertFalse(Instagram.isValidInstagram("has-hyphen")); // hyphen not allowed
        assertFalse(Instagram.isValidInstagram("has space")); // spaces not allowed
        assertFalse(Instagram.isValidInstagram("too_long_instagram_handle_123456")); // more than 30 chars
        assertFalse(Instagram.isValidInstagram("@@@@")); // invalid characters

        // valid instagram handles
        assertTrue(Instagram.isValidInstagram("a")); // minimum length
        assertTrue(Instagram.isValidInstagram("johnDoe123"));
        assertTrue(Instagram.isValidInstagram("mary.foodie"));
        assertTrue(Instagram.isValidInstagram("sarah_eats"));
        assertTrue(Instagram.isValidInstagram("@sarah_eats")); // optional @ prefix
        assertTrue(Instagram.isValidInstagram("a_b.c1")); // mix of allowed characters
        assertTrue(Instagram.isValidInstagram("abc123456789012345678901234567")); // exactly 30 chars
    }

    @Test
    public void equals() {
        Instagram instagram = new Instagram("valid_handle");

        // same values -> returns true
        assertTrue(instagram.equals(new Instagram("valid_handle")));

        // same values with @ prefix normalization -> returns true
        assertTrue(instagram.equals(new Instagram("@valid_handle")));

        // same object -> returns true
        assertTrue(instagram.equals(instagram));

        // null -> returns false
        assertFalse(instagram.equals(null));

        // different types -> returns false
        assertFalse(instagram.equals(5.0f));

        // different values -> returns false
        assertFalse(instagram.equals(new Instagram("other_handle")));
    }
}

