package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The customer index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_ORDERS_LISTED_OVERVIEW = "%1$d orders listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_MISSING_CONTACT_METHOD =
            "At least one contact method (phone, email, or address) must be provided.";
    public static final String MESSAGE_NO_SAVED_ADDRESS =
            "Customer has no saved address. Please specify delivery address with a/ or use a/PICKUP for pickup orders.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName());

        person.getPhone().ifPresent(p ->
                builder.append("; Phone: ").append(p)
        );

        person.getEmail().ifPresent(e ->
                builder.append("; Email: ").append(e)
        );

        person.getAddress().ifPresent(a ->
                builder.append("; Address: ").append(a)
        );

        builder.append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code order} for display to the user.
     */
    public static String format(Order order, String customerName) {
        return String.format(
                "%s (x%s) to %s.\n"
                        + "Delivery to: %s\n"
                        + "At: %s | Status: %s",
                order.getItem(),
                order.getQuantity(),
                customerName,
                order.getAddress(),
                order.getDeliveryTime(),
                order.getStatus()
        );
    }
}
