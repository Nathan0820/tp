package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DeliveryTimeTest {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeliveryTime(null));
    }

    @Test
    public void constructor_invalidFormat_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new DeliveryTime(""));
        assertThrows(IllegalArgumentException.class, () -> new DeliveryTime("not a date"));
        assertThrows(IllegalArgumentException.class, () -> new DeliveryTime("2025/12/12 1200"));
        assertThrows(IllegalArgumentException.class, () -> new DeliveryTime("2025-12-12 12:00"));
        assertThrows(IllegalArgumentException.class, () -> new DeliveryTime("2025-1-1 1200"));
    }

    @Test
    public void constructor_pastDate_throwsIllegalArgumentException() {
        String past = LocalDateTime.now().minusDays(1).format(FORMATTER);
        assertThrows(IllegalArgumentException.class, () -> new DeliveryTime(past));
    }

    @Test
    public void isValidFormat() {
        // null -> throws
        assertThrows(NullPointerException.class, () -> DeliveryTime.isValidFormat(null));

        // invalid formats
        assertFalse(DeliveryTime.isValidFormat(""));
        assertFalse(DeliveryTime.isValidFormat("2025-12-12"));        // missing time
        assertFalse(DeliveryTime.isValidFormat("2025-12-12 12:00"));  // colon not allowed
        assertFalse(DeliveryTime.isValidFormat("2025/12/12 1200"));   // wrong separator
        assertFalse(DeliveryTime.isValidFormat("2025-1-1 1200"));     // wrong digit count

        // valid formats
        assertTrue(DeliveryTime.isValidFormat("2025-12-12 1200"));
        assertTrue(DeliveryTime.isValidFormat("2030-01-01 0000"));
    }

    @Test
    public void isInFuture() {
        // past
        String past = LocalDateTime.now().minusHours(1).format(FORMATTER);
        assertFalse(DeliveryTime.isInFuture(past));

        // future
        String future = LocalDateTime.now().plusHours(1).format(FORMATTER);
        assertTrue(DeliveryTime.isInFuture(future));
    }

    @Test
    public void equals() {
        String future = LocalDateTime.now().plusDays(1).format(FORMATTER);

        DeliveryTime dt = new DeliveryTime(future);

        // same values -> true
        assertTrue(dt.equals(new DeliveryTime(future)));

        // same object -> true
        assertTrue(dt.equals(dt));

        // null -> false
        assertFalse(dt.equals(null));

        // different type -> false
        assertFalse(dt.equals(5));

        // different value -> false
        String otherFuture = LocalDateTime.now().plusDays(2).format(FORMATTER);
        assertFalse(dt.equals(new DeliveryTime(otherFuture)));
    }
}
