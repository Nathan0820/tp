package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Status("invalid"));
        assertThrows(IllegalArgumentException.class, () -> new Status("done"));
        assertThrows(IllegalArgumentException.class, () -> new Status("123"));
        assertThrows(IllegalArgumentException.class, () -> new Status("ready!"));
    }

    @Test
    public void isValidStatus() {
        // null -> throws
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid statuses
        assertFalse(Status.isValidStatus("")); // empty
        assertFalse(Status.isValidStatus(" ")); // spaces
        assertFalse(Status.isValidStatus("done")); // not allowed
        assertFalse(Status.isValidStatus("ready!")); // invalid char
        assertFalse(Status.isValidStatus("PREPARE")); // partial match
        assertFalse(Status.isValidStatus("DELIVER")); // partial match
        assertFalse(Status.isValidStatus("ready")); // lowercase not accepted
        assertFalse(Status.isValidStatus("ReAdY")); // mixed case not accepted

        // valid statuses (must match regex exactly)
        assertTrue(Status.isValidStatus("PREPARING"));
        assertTrue(Status.isValidStatus("READY"));
        assertTrue(Status.isValidStatus("DELIVERED"));
        assertTrue(Status.isValidStatus("CANCELLED"));
    }

    @Test
    public void constructor_lowercaseInput_convertsToUppercase() {
        Status s = new Status("ready");
        assertTrue(s.value.equals("READY"));
    }

    @Test
    public void constructor_mixedCaseInput_convertsToUppercase() {
        Status s = new Status("ReAdY");
        assertTrue(s.value.equals("READY"));
    }

    @Test
    public void equals() {
        Status s = new Status("PREPARING");

        // same values -> true
        assertTrue(s.equals(new Status("PREPARING")));

        // same object -> true
        assertTrue(s.equals(s));

        // case-insensitive equality
        assertTrue(new Status("ready").equals(new Status("READY")));

        // null -> false
        assertFalse(s.equals(null));

        // different type -> false
        assertFalse(s.equals(5));

        // different value -> false
        assertFalse(s.equals(new Status("READY")));
    }
}

