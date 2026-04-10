package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;
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

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The customer index provided is invalid. "
            + "Please use an index from the displayed customer list.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d customers listed matching %2$s ";
    public static final String MESSAGE_INVALID_ORDER_DISPLAYED_INDEX = "The order index provided is invalid. "
            + "Please use an index from the displayed order list.";;
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_MISSING_CONTACT_METHOD =
            "At least one contact method (phone, Facebook, or Instagram) must be provided.";
    public static final String MESSAGE_NO_CONTACT_METHOD_AFTER_EDIT =
            "The edited customer must still have at least one contact method "
                    + "(phone, Facebook, or Instagram).";
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
     * Formats the {@code order} for display to the user.
     */
    public static String format(Order order, String customerName) {
        return String.format(
                "%s (x%s) for %s.\n"
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

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();

        builder.append(person.getName());
        appendContactInfo(builder, person);
        appendAddress(builder, person);
        appendRemark(builder, person);
        appendTags(builder, person);

        return builder.toString();
    }

    private static void appendContactInfo(StringBuilder builder, Person person) {
        List<String> contactParts = new ArrayList<>();

        person.getPhone().ifPresent(p -> contactParts.add("Phone: " + p));
        person.getFacebook().ifPresent(fb -> contactParts.add("Facebook: " + fb.getDisplayValue()));
        person.getInstagram().ifPresent(ig -> contactParts.add("Instagram: " + ig.getDisplayValue()));

        if (!contactParts.isEmpty()) {
            builder.append("\n").append(String.join(" | ", contactParts));
        }
    }

    private static void appendAddress(StringBuilder builder, Person person) {
        person.getAddress().ifPresent(a ->
                builder.append("\nAddress: ").append(a)
        );
    }

    private static void appendRemark(StringBuilder builder, Person person) {
        person.getRemark().ifPresent(r ->
                builder.append("\nRemark: ").append(r)
        );
    }

    private static void appendTags(StringBuilder builder, Person person) {
        String tags = person.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.joining(", "));

        builder.append("\nTags: ").append(tags.isEmpty() ? "-" : tags);
    }
}
