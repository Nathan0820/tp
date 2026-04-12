package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final int MAX_NAME_LENGTH = 100;
    public static final String MESSAGE_CONSTRAINTS = """
        Customer name must:
        • be 1 to %d characters long
        • begin with a letter or a number
        • contain only letters, numbers, spaces, and the following punctuation: ' / ( ) -
        """.formatted(MAX_NAME_LENGTH);

    /*
     * The first character of the name must not be whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}' /()-]{0,99}";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name.trim(); // defensive trimming
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        String normalizedThis = normalizeFullName(fullName);
        String normalizedOther = normalizeFullName(otherName.fullName);
        return normalizedThis.equals(normalizedOther);
    }

    /**
     * Normalizes a full name for consistent comparison and storage.
     * Converts the name to lowercase and collapses consecutive whitespace
     * characters into a single space.
     *
     * @param fullName The name to normalize.
     * @return The normalized name in lowercase with single spaces.
     */
    private static String normalizeFullName(String fullName) {
        return fullName.toLowerCase().replaceAll("\\s+", " ");
    }

    @Override
    public int hashCode() {
        return normalizeFullName(fullName).hashCode();
    }

}
