package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Item in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidItem(String)}
 */
public class Item {

    public static final String MESSAGE_CONSTRAINTS =
            "Item name must be alphanumeric and not empty.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs an {@code Item}.
     *
     * @param itemName A valid item name.
     */
    public Item(String itemName) {
        requireNonNull(itemName);
        checkArgument(isValidItem(itemName), MESSAGE_CONSTRAINTS);
        this.value = itemName;
    }

    /**
     * Returns true if a given string is a valid item name.
     */
    public static boolean isValidItem(String test) {
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
        if (!(other instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) other;
        return value.equals(otherItem.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
