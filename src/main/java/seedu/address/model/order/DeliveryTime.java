package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a delivery date and time for an Order.
 * Guarantees: immutable; is valid as declared in {@link #isValidFormat(String)} and {@link #isInFuture(String)}
 */
public class DeliveryTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Usage: yyyy-mm-dd hhmm, e.g. 2026-02-20 2359";

    public static final String MESSAGE_CONSTRAINTS_FUTURE =
            "Date/time must be valid and in the future";

    public static final String VALIDATION_REGEX =
            "\\d{4}-\\d{2}-\\d{2} \\d{4}";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public final String value;

    /**
     * Constructs a {@code Datetime}.
     *
     * @param datetime A valid datetime.
     */
    public DeliveryTime(String datetime) {
        requireNonNull(datetime);
        checkArgument(isValidFormat(datetime), MESSAGE_CONSTRAINTS);
        checkArgument(isInFuture(datetime), MESSAGE_CONSTRAINTS_FUTURE);
        value = datetime;
    }

    /**
     * Returns true if the input matches the yyyy-mm-dd hhmm format.
     */
    public static boolean isValidFormat(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if datetime is after the current system time.
     */
    public static boolean isInFuture(String test) {
        try {
            LocalDateTime dt = LocalDateTime.parse(test, FORMATTER);
            return dt.isAfter(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            // Should never happen if format already validated
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
