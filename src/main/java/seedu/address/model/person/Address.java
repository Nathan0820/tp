package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final int MAX_ADDRESS_LENGTH = 200;
    public static final String MESSAGE_CONSTRAINTS =
            "Address must only contain alphanumeric characters, spaces and "
            + "the following special characters: ,.'/#&()-.\n"
            + "Each section must start with alphanumeric characters, and must not exceed "
            + MAX_ADDRESS_LENGTH + " characters total.";

    /*
     * The first character of the address must not be a whitespace
     * The address should only contain alphanumeric characters, spaces, and the following special characters: ,.'/#&()-.
     * The address must not exceed 200 characters in length.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9#][A-Za-z0-9 ,.'/#&()-]{0,199}$";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid address.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Address)) {
            return false;
        }

        Address otherAddress = (Address) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
