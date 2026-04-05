package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Facebook username in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFacebook(String)}
 */
public class Facebook {

    public static final String MESSAGE_CONSTRAINTS =
            "Facebook username must be 5 to 50 characters and contain only letters, numbers, and periods.\n"
                    + "No leading, trailing, or consecutive periods are allowed. " + "A leading @ is optional.";

    /*
     * Username: alphanumeric, periods. 5-50 chars. No leading/trailing periods.
     * No consecutive periods.
     */
    public static final String VALIDATION_REGEX = "^@?(?!\\.)(?!.*\\.\\.)[A-Za-z0-9.]{5,50}(?<!\\.)$";

    public final String value;

    /**
     * Constructs a {@code Facebook}.
     *
     * @param facebook A valid Facebook username (@ prefix optional).
     */
    public Facebook(String facebook) {
        requireNonNull(facebook);
        String trimmed = facebook.trim(); // defensive trimming
        checkArgument(isValidFacebook(trimmed), MESSAGE_CONSTRAINTS);
        value = trimmed.startsWith("@") ? trimmed.substring(1) : trimmed;
    }

    /**
     * Returns if a given string is a valid Facebook username.
     */
    public static boolean isValidFacebook(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the Facebook username with @ prefix for display.
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
        if (!(other instanceof Facebook)) {
            return false;
        }
        Facebook otherFacebook = (Facebook) other;

        // Remove periods and capitalisation for equality check as
        // Facebook usernames are period-insensitive and case-insensitive
        String thisValue = canonicalize(value);
        String otherValue = canonicalize(otherFacebook.value);
        return thisValue.equals(otherValue);
    }

    @Override
    public int hashCode() {
        return canonicalize(value).hashCode();
    }

    private static String canonicalize(String facebookValue) {
        return facebookValue.replace(".", "").toLowerCase();
    }

}
