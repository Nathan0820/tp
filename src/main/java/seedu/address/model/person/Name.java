package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Name must contain only alphanumeric characters, spaces and apostrophes, and it must not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}' ]*";

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
    public String normalizeFullName(String fullName) {
        return fullName.toLowerCase().replaceAll("\\s+", " ");
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
