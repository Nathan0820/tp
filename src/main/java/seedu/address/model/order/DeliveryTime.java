package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a delivery date and time for an Order.
 * Guarantees: immutable; value is valid as declared in {@link #isValidFormat(String)} and {@link #isValidDate(String)}
 */
public class DeliveryTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Delivery time must be in the format: yyyy-mm-dd hhmm, e.g. 2026-05-20 2300.";

    public static final String MESSAGE_CONSTRAINTS_VALID =
            "Delivery time must be valid.";

    public static final String VALIDATION_REGEX =
            "\\d{4}-\\d{2}-\\d{2} \\d{4}";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public final String value;

    /**
     * Constructs a {@code DeliveryTime}.
     *
     * @param datetime A valid datetime.
     */
    public DeliveryTime(String datetime) {
        requireNonNull(datetime);
        checkArgument(isValidFormat(datetime), MESSAGE_CONSTRAINTS);
        checkArgument(isValidDate(datetime), MESSAGE_CONSTRAINTS);
        value = datetime;
    }

    /**
     * Returns true if the input matches the yyyy-mm-dd hhmm format.
     */
    public static boolean isValidFormat(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if delivery time is valid.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDateTime.parse(test, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if delivery time is after current system time.
     */
    public static boolean isInFuture(String test) {
        try {
            LocalDateTime dt = LocalDateTime.parse(test, FORMATTER);
            return dt.isAfter(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            return false;
        }
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
        if (!(other instanceof DeliveryTime)) {
            return false;
        }
        DeliveryTime otherDt = (DeliveryTime) other;
        return value.equals(otherDt.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
