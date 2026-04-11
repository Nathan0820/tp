package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Item's quantity in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity {
    public static final String MESSAGE_CONSTRAINTS =
            "Item quantity must be a positive whole number.";

    public static final String VALIDATION_REGEX = "[1-9][0-9]*";

    private static final int THRESHOLD = 199;

    public final String value;

    public final Integer intValue;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid quantity.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        value = quantity;
        intValue = Integer.parseInt(quantity);
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the quantity exceeds the predefined threshold.
     */
    public boolean isLarge() {
        return this.intValue > THRESHOLD;
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
        if (!(other instanceof Quantity)) {
            return false;
        }

        Quantity otherQuantity = (Quantity) other;
        return value.equals(otherQuantity.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
