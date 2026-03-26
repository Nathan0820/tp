package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args, "Find command arguments cannot be null");
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_FACEBOOK,
                PREFIX_TAG, PREFIX_INSTAGRAM, PREFIX_REMARK);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_FACEBOOK,
                PREFIX_TAG, PREFIX_INSTAGRAM, PREFIX_REMARK);
        Map<PersonContainsKeywordsPredicate.SearchType, String> keywordsMap = new HashMap<>();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            keywordsMap.put(PersonContainsKeywordsPredicate.SearchType.NAME,
                    getNonEmptyValue(argMultimap, PREFIX_NAME));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            keywordsMap.put(PersonContainsKeywordsPredicate.SearchType.PHONE,
                    getNonEmptyValue(argMultimap, PREFIX_PHONE));
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            keywordsMap.put(PersonContainsKeywordsPredicate.SearchType.ADDRESS,
                    getNonEmptyValue(argMultimap, PREFIX_ADDRESS));
        }

        if (argMultimap.getValue(PREFIX_FACEBOOK).isPresent()) {
            keywordsMap.put(PersonContainsKeywordsPredicate.SearchType.FACEBOOK,
                    getNonEmptyValue(argMultimap, PREFIX_FACEBOOK));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            keywordsMap.put(PersonContainsKeywordsPredicate.SearchType.TAG,
                    getNonEmptyValue(argMultimap, PREFIX_TAG));
        }

        if (argMultimap.getValue(PREFIX_INSTAGRAM).isPresent()) {
            keywordsMap.put(PersonContainsKeywordsPredicate.SearchType.INSTAGRAM,
                    getNonEmptyValue(argMultimap, PREFIX_INSTAGRAM));
        }

        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            keywordsMap.put(PersonContainsKeywordsPredicate.SearchType.REMARK,
                    getNonEmptyValue(argMultimap, PREFIX_REMARK));
        }
        if (keywordsMap.isEmpty()) {
            return new FindCommand(new PersonContainsKeywordsPredicate(trimmedArgs, true, keywordsMap));
        }
        return new FindCommand(new PersonContainsKeywordsPredicate(trimmedArgs, false, keywordsMap));
    }

    /**
     * Helper function to make sure the input value is non-empty.
     */
    private String getNonEmptyValue(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        requireNonNull(argMultimap);
        requireNonNull(prefix);
        assert argMultimap.getValue(prefix).isPresent() : "getNonEmptyValue requires prefix to be present";
        String value = argMultimap.getValue(prefix).get().trim();
        if (value.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return value;
    }
}
