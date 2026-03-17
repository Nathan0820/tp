package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Instagram handle in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInstagram(String)}
 */
public class Instagram {

    public static final String MESSAGE_CONSTRAINTS =
            "Instagram handles should be 1-30 characters and contain only letters, numbers, underscores, and periods."
            + " They should not end with a period or contain consecutive periods.";
    public static final String VALIDATION_REGEX =
            "^@?(?!.*\\.\\.)(?!.*\\.$)(?!^\\.)[A-Za-z0-9._]{1,30}$";

    public final String value;

    /**
     * Constructs an {@code Instagram} handle.
     *
     * @param instagram A valid instagram handle.
     */
    public Instagram(String instagram) {
        requireNonNull(instagram);
        checkArgument(isValidInstagram(instagram), MESSAGE_CONSTRAINTS);
        value = instagram.startsWith("@")
                ? instagram.substring(1) : instagram; // strip leading @ for storage
    }

    /**
     * Returns if a given string is a valid Instagram handle.
     */
    public static boolean isValidInstagram(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the Instagram handle with @ prefix for display.
     */
    public String getDisplayValue() {
        return "@" + value;
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
        if (!(other instanceof Instagram)) {
            return false;
        }

        Instagram otherInstagram = (Instagram) other;
        return value.equals(otherInstagram.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
