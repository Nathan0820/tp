package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Instagram handle in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInstagram(String)}
 */
public class Instagram {

    public static final String MESSAGE_CONSTRAINTS =
            "Instagram handle must be 1 to 30 characters and contain only "
                    + "letters, numbers, underscores, and periods.\n"
                    + "No trailing periods or consecutive periods are allowed. "
                    + "A leading @ is optional.";

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
        String trimmed = instagram.trim();
        checkArgument(isValidInstagram(trimmed), MESSAGE_CONSTRAINTS);
        value = trimmed.startsWith("@") ? trimmed.substring(1) : trimmed; // trim leading @ for storage
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
        return canonicalize(value).equals(canonicalize(otherInstagram.value));
    }

    @Override
    public int hashCode() {
        return canonicalize(value).hashCode();
    }

    private static String canonicalize(String instagramValue) {
        return instagramValue.toLowerCase();
    }
}
