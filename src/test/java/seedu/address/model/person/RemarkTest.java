package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        String emptyString = "";
        assertThrows(IllegalArgumentException.class, () -> new Remark(emptyString));

        String whitespacesOnly = "  ";
        assertThrows(IllegalArgumentException.class, () -> new Remark(whitespacesOnly));
    }

    @Test
    public void isValidRemark() {
        // null remark
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // invalid remarks
        assertFalse(Remark.isValidRemark(""));
        assertFalse(Remark.isValidRemark(" "));
        assertFalse(Remark.isValidRemark("a".repeat(501))); // more than 500 characters

        // valid remarks

        // 1 character
        assertTrue(Remark.isValidRemark("-"));

        // between 1 and 500 characters (with or without special characters)
        assertTrue(Remark.isValidRemark("Regular customer"));
        assertTrue(Remark.isValidRemark(
                "Prefers weekend delivery, prefers extra spicy, usually takes the set meal."));
        assertTrue(Remark.isValidRemark("N.A."));

        // exactly 500 characters
        assertTrue(Remark.isValidRemark("a".repeat(500)));

        // contains only special characters
        assertTrue(Remark.isValidRemark("!@#$%^&*()_+"));

    }

    @Test
    public void equals() {
        Remark remark = new Remark("Valid Remark");

        // same values -> returns true
        assertTrue(remark.equals(new Remark("Valid Remark")));

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different types -> returns false
        assertFalse(remark.equals(5.0f));

        // different values -> returns false
        assertFalse(remark.equals(new Remark("Other Valid Remark")));
    }
}

