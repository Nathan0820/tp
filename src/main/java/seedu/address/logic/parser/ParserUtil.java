package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Prefixes recognised by {@code add} and {@code edit} for customers (used to detect stray {@code x/}-style tokens).
     */
    public static final Prefix[] PREFIXES_FOR_PERSON_COMMAND = {
        PREFIX_NAME, PREFIX_PHONE, PREFIX_FACEBOOK, PREFIX_INSTAGRAM, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_TAG
    };

    /**
     * Prefixes recognised by {@code order} (used to detect stray {@code x/}-style tokens).
     */
    public static final Prefix[] PREFIXES_FOR_ORDER_COMMAND = {
        PREFIX_ITEM, PREFIX_QUANTITY, PREFIX_DATETIME, PREFIX_ADDRESS, PREFIX_STATUS
    };

    private static final Pattern EMBEDDED_PREFIX_LIKE_TOKEN = Pattern.compile("\\s+([a-zA-Z][a-zA-Z0-9]*)/");

    /**
     * Ensures {@code value} does not contain a substring that looks like an unknown field prefix (e.g. {@code x/hello})
     * that is not one of the allowed {@link Prefix}
     *
     * @return {@code value} unchanged, for convenient chaining into {@code parseX} methods
     */
    public static String ensureNoUnsupportedPrefixTokensInValue(String value, Prefix... allowedPrefixes)
            throws ParseException {
        requireNonNull(value);
        requireNonNull(allowedPrefixes);
        Set<String> allowed = Arrays.stream(allowedPrefixes).map(Prefix::getPrefix).collect(Collectors.toSet());
        Matcher m = EMBEDDED_PREFIX_LIKE_TOKEN.matcher(value);
        while (m.find()) {
            String token = m.group(1) + "/";
            if (!allowed.contains(token)) {
                throw new ParseException(String.format(Messages.MESSAGE_UNSUPPORTED_PREFIX, token));
            }
        }
        return value;
    }

    /**
     * Ensures {@code value} does not contain any substring that resembles an
     * unsupported prefix token (e.g. {@code x/hello}) that is not one of the
     * given {@code allowedPrefixes}. Uses {@code errorMessage} when reporting
     * unsupported prefixes.
     *
     * @return {@code value} unchanged, allowing convenient chaining into {@code parseX} methods
     */
    public static String ensureNoUnsupportedPrefixTokensInValue(
            String value, String errorMessage, Prefix... allowedPrefixes
    ) throws ParseException {
        requireNonNull(value);
        requireNonNull(allowedPrefixes);

        Set<String> allowed = Arrays.stream(allowedPrefixes)
                .map(Prefix::getPrefix)
                .collect(Collectors.toSet());

        Matcher m = EMBEDDED_PREFIX_LIKE_TOKEN.matcher(value);
        while (m.find()) {
            String token = m.group(1) + "/";
            if (!allowed.contains(token)) {
                throw new ParseException(String.format(errorMessage, token));
            }
        }
        return value;
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Replaces each literal {@code \/} with {@code /}.
     */
    public static String unescapeNameSlashes(String name) {
        requireNonNull(name);
        return name.replace("\\/", "/");
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        String unescaped = unescapeNameSlashes(trimmedName);
        if (!Name.isValidName(unescaped)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(unescaped);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String facebook} into a {@code Facebook}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code facebook} is invalid.
     */
    public static Facebook parseFacebook(String facebook) throws ParseException {
        requireNonNull(facebook);
        String trimmedFacebook = facebook.trim();
        if (!Facebook.isValidFacebook(trimmedFacebook)) {
            throw new ParseException(Facebook.MESSAGE_CONSTRAINTS);
        }
        return new Facebook(trimmedFacebook);
    }

    /**
     * Parses a {@code String instagram} into an {@code Instagram}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code instagram} handle is invalid.
     */
    public static Instagram parseInstagram(String instagram) throws ParseException {
        requireNonNull(instagram);
        String trimmedInstagram = instagram.trim();
        if (!Instagram.isValidInstagram(trimmedInstagram)) {
            throw new ParseException(Instagram.MESSAGE_CONSTRAINTS);
        }
        return new Instagram(trimmedInstagram);
    }

    /**
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        ensureNoUnsupportedPrefixTokensInValue(trimmedTag, PREFIXES_FOR_PERSON_COMMAND);
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String item} into an {@code Item}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Item} is invalid.
     */
    public static Item parseItem(String item) throws ParseException {
        requireNonNull(item);
        String trimmedItem = item.trim();
        if (!Item.isValidItem(trimmedItem)) {
            throw new ParseException(Item.MESSAGE_CONSTRAINTS);
        }
        return new Item(trimmedItem);
    }

    /**
     * Parses a {@code String quantity} into an {@code Quantity}.
     * Leading and trailing whitespaces will be trimmed.
     * Leading zeros are removed before validation (e.g., "01" -> "1").
     *
     * @throws ParseException if the given {@code Quantity} is invalid.
     */
    public static Quantity parseQuantity(String quantity) throws ParseException {
        requireNonNull(quantity);
        String trimmedQuantity = quantity.trim();
        String normalized = trimmedQuantity.replaceFirst("^0+(?!$)", "");

        if (!Quantity.isValidQuantity(normalized)) {
            throw new ParseException(Quantity.MESSAGE_CONSTRAINTS);
        }
        return new Quantity(normalized);
    }

    /**
     * Parses a {@code String deliveryTime} into an {@code DeliveryTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code DeliveryTime} is invalid.
     */
    public static DeliveryTime parseDeliveryTime(String deliveryTime) throws ParseException {
        requireNonNull(deliveryTime);
        String trimmedDeliveryTime = deliveryTime.trim();

        if (!DeliveryTime.isValidFormat(trimmedDeliveryTime)) {
            throw new ParseException(DeliveryTime.MESSAGE_CONSTRAINTS);
        }

        if (!DeliveryTime.isValidDate(trimmedDeliveryTime)) {
            throw new ParseException(DeliveryTime.MESSAGE_CONSTRAINTS_VALID);
        }

        return new DeliveryTime(trimmedDeliveryTime);
    }

    /**
     * Parses a {@code String status} into an {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        try {
            return new Status(trimmedStatus); // constructor handles uppercase + validation
        } catch (IllegalArgumentException e) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
    }
}
