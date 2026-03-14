package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;

import java.util.Optional;

import seedu.address.logic.commands.FindOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.OrderContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindOrderCommand object
 */
public class FindOrderCommandParser implements Parser<FindOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindOrderCommand
     * and returns a FindOrderCommand object for execution.
     */
    public FindOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_ITEM, PREFIX_ADDRESS, PREFIX_CUSTOMER);

        Optional<String> itemSearch = argMultimap.getValue(PREFIX_ITEM);
        Optional<String> addressSearch = argMultimap.getValue(PREFIX_ADDRESS);
        Optional<String> customerSearch = argMultimap.getValue(PREFIX_CUSTOMER);

        int count = 0;
        if (itemSearch.isPresent()) {
            count++;
        }
        if (addressSearch.isPresent()) {
            count++;
        }
        if (customerSearch.isPresent()) {
            count++;
        }

        if (count != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
        }

        if (itemSearch.isPresent()) {
            return new FindOrderCommand(
                    new OrderContainsKeywordsPredicate(
                            OrderContainsKeywordsPredicate.SearchType.ITEM,
                            itemSearch.get()));
        }

        if (addressSearch.isPresent()) {
            return new FindOrderCommand(
                    new OrderContainsKeywordsPredicate(
                            OrderContainsKeywordsPredicate.SearchType.ADDRESS,
                            addressSearch.get()));
        }

        // customerSearch must be present
        return new FindOrderCommand(
                new OrderContainsKeywordsPredicate(
                        OrderContainsKeywordsPredicate.SearchType.CUSTOMER,
                        customerSearch.get()));
    }
}
