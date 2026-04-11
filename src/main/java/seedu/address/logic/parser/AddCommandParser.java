package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_CONTACT_METHOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Logger logger = LogsCenter.getLogger(AddCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.info("Parsing AddCommand with args: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_PHONE, PREFIX_FACEBOOK, PREFIX_INSTAGRAM,
                        PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_TAG);

        // Only name is mandatory
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning("Missing required name or unexpected preamble: " + argMultimap.getPreamble());
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_FACEBOOK, PREFIX_INSTAGRAM, PREFIX_ADDRESS, PREFIX_REMARK);
        Name name = ParserUtil.parseName(ParserUtil.ensureNoUnsupportedPrefixTokensInValue(
                argMultimap.getValue(PREFIX_NAME).get(), ParserUtil.PREFIXES_FOR_PERSON_COMMAND));

        // Check at least one contact method is provided
        if (!hasAtLeastOneContactMethod(argMultimap, PREFIX_PHONE, PREFIX_FACEBOOK, PREFIX_INSTAGRAM)) {
            logger.warning("AddCommand rejected: no contact method provided.");
            throw new ParseException(MESSAGE_MISSING_CONTACT_METHOD);
        }

        // Parse optional fields - pass null if not present
        Phone phone = argMultimap.getValue(PREFIX_PHONE).isPresent()
                ? ParserUtil.parsePhone(ParserUtil.ensureNoUnsupportedPrefixTokensInValue(
                        argMultimap.getValue(PREFIX_PHONE).get(), ParserUtil.PREFIXES_FOR_PERSON_COMMAND))
                : null;

        Facebook facebook = argMultimap.getValue(PREFIX_FACEBOOK).isPresent()
                ? ParserUtil.parseFacebook(ParserUtil.ensureNoUnsupportedPrefixTokensInValue(
                        argMultimap.getValue(PREFIX_FACEBOOK).get(), ParserUtil.PREFIXES_FOR_PERSON_COMMAND))
                : null;

        Instagram instagram = argMultimap.getValue(PREFIX_INSTAGRAM).isPresent()
                ? ParserUtil.parseInstagram(ParserUtil.ensureNoUnsupportedPrefixTokensInValue(
                        argMultimap.getValue(PREFIX_INSTAGRAM).get(), ParserUtil.PREFIXES_FOR_PERSON_COMMAND))
                : null;

        Address address = argMultimap.getValue(PREFIX_ADDRESS).isPresent()
                ? ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get())
                : null;

        Remark remark = argMultimap.getValue(PREFIX_REMARK).isPresent()
                ? ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get())
                : null;

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, facebook, instagram, address, remark, tagList);

        logger.info("Successfully parsed AddCommand for customer: " + name);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if at least one of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean hasAtLeastOneContactMethod(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
