package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("   mary-anne")); // starts with spaces
        assertFalse(Name.isValidName("^")); // one invalid non-alphanumeric character
        assertFalse(Name.isValidName("*Mr Tan")); // starts with an invalid character
        assertFalse(Name.isValidName("peter**")); // end with invalid characters
        assertFalse(Name.isValidName("a".repeat(101))); // more than 100 characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("john/doe-smith")); // with slash and hyphen
        assertTrue(Name.isValidName("Jon Tan (Clementi)")); // with parentheses descriptor
        assertTrue(Name.isValidName("Mary-Anne (Bedok)")); // hyphenated name with parentheses descriptor
        assertTrue(Name.isValidName("a".repeat(100))); // exactly 100 characters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names between 1 and 100 characters
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // same normalized values -> returns true
        assertTrue(new Name("valid       name").equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }

    @Test
    public void hashCode_sameNormalizedName_sameHashCode() {
        Name name = new Name("Valid Name");
        Name sameNormalized = new Name("valid   name");

        assertEquals(name.hashCode(), sameNormalized.hashCode());
    }
}
