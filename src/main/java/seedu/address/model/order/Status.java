package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the status of an Order.
 * Guarantees: immutable; status is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {
    public static final String MESSAGE_CONSTRAINTS =
            "Status must be one of the following: PREPARING, READY, DELIVERED, or CANCELLED.";

    // Only allow these three statuses (case-insensitive input allowed, store as uppercase)
    public static final String VALID_STATUSES = "PREPARING|READY|DELIVERED|CANCELLED";

    public static final Status DEFAULT_STATUS = new Status("PREPARING");

    public final String value;

    /**
     * Constructs an {@code OrderStatus}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        status = status.toUpperCase();
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        this.value = status;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(VALID_STATUSES);
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

        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return value.equals(otherStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
