package seedu.address.logic;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

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
            + "Please use an index from the displayed order list.";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_MISSING_CONTACT_METHOD =
            "At least one contact method (phone, Facebook, or Instagram) must be provided.";
    public static final String MESSAGE_NO_CONTACT_METHOD_AFTER_EDIT =
            "The edited customer must still have at least one contact method (phone, Facebook, or Instagram).";
    public static final String MESSAGE_NO_SAVED_ADDRESS =
            "Customer has no saved address. Please specify delivery address with a/ or use a/PICKUP for pickup orders.";
    public static final String MESSAGE_UNSUPPORTED_PREFIX =
            "Unsupported prefix '%1$s' in this command. Valid prefixes: n/, p/, fb/, ig/, a/, r/, t/.";

    public static final String MESSAGE_WARNING_DUPLICATE_CONTACT = "WARNING: Duplicate contact details detected. "
            + "This is allowed, but please verify.%nMatched fields: %s%n%s%n%n";
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
        assert person != null;

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
        person.getAddress().ifPresent(a -> builder.append("\nAddress: ").append(a));
    }

    private static void appendRemark(StringBuilder builder, Person person) {
        person.getRemark().ifPresent(r -> builder.append("\nRemark: ").append(r));
    }

    private static void appendTags(StringBuilder builder, Person person) {
        String tags = person.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.joining(", "));

        builder.append("\nTags: ").append(tags.isEmpty() ? "-" : tags);
    }

    /**
     * Formats a non-blocking warning with dynamic find examples using the person's matched values.
     */
    public static String formatDuplicateContactWarning(Set<String> matchedFields, Person person) {
        assert matchedFields != null;
        assert person != null;

        String fields = String.join(", ", matchedFields);
        List<String> examples = new ArrayList<>();

        if (matchedFields.contains(Person.MATCH_FIELD_PHONE)) {
            person.getPhone().ifPresent(phone -> examples.add("find " + PREFIX_PHONE + phone));
        }
        if (matchedFields.contains(Person.MATCH_FIELD_FACEBOOK)) {
            person.getFacebook().ifPresent(facebook -> examples.add("find " + PREFIX_FACEBOOK + facebook));
        }
        if (matchedFields.contains(Person.MATCH_FIELD_INSTAGRAM)) {
            person.getInstagram().ifPresent(instagram -> examples.add("find " + PREFIX_INSTAGRAM + instagram));
        }

        String nextStep = examples.isEmpty()
                ? "Run find with the matching value(s) to review." // defensive safeguard for empty matchedFields
                : "Try: " + String.join(" or ", examples);
        return String.format(MESSAGE_WARNING_DUPLICATE_CONTACT, fields, nextStep);
    }
}
