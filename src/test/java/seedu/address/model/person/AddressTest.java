package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only
        assertFalse(Address.isValidAddress("a".repeat(201))); // more than 200 characters
        assertFalse(Address.isValidAddress("-123, City, 12345")); // starts with dash

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, 01355")); // valid format
        assertTrue(Address.isValidAddress("123 Main Street, Springfield, 12345")); // standard format
        assertTrue(Address.isValidAddress("Apt 5-6/7, Downtown City, 98765")); // with special characters
        // exactly 200 characters: street(~80) + city(~60) + postal(~50)
        assertTrue(Address.isValidAddress("123 Main Street")); // missing city and postal code
        assertTrue(Address.isValidAddress("123 Main Street, Springfield")); // missing postal code
        assertTrue(Address.isValidAddress("123 Main Street, Somewhere, 12345")); // exactly 200 or less
    }

    @Test
    public void equals() {
        Address address = new Address("Valid Address");

        // same values -> returns true
        assertTrue(address.equals(new Address("Valid Address")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("Other Valid Address")));
    }
}
