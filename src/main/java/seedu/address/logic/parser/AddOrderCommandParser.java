package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;

/**
 * Parses input arguments and creates a new AddOrderCommand object
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {

    private static final Logger logger = Logger.getLogger(AddOrderCommandParser.class.getName());

    /**
     * Parses the given {@code String} of arguments in the context of the AddOrderCommand
     * and returns an AddOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddOrderCommand parse(String args) throws ParseException {
        logger.log(Level.INFO, "Starting AddOrderCommand parsing with args: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ITEM, PREFIX_QUANTITY, PREFIX_DATETIME,
                        PREFIX_ADDRESS, PREFIX_STATUS);

        String preamble = argMultimap.getPreamble();

        if (preamble.isEmpty()) {
            logger.log(Level.WARNING, "Missing customer index in preamble");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        Index customerIndex;
        try {
            customerIndex = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "Invalid index format: " + preamble, pe);
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_ITEM, PREFIX_QUANTITY, PREFIX_DATETIME)) {
            logger.log(Level.WARNING, "Missing required prefixes");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ITEM, PREFIX_QUANTITY, PREFIX_DATETIME,
                PREFIX_ADDRESS, PREFIX_STATUS);

        logger.log(Level.FINE, "Parsing item, quantity, and delivery time");

        Item item = ParserUtil.parseItem(argMultimap.getValue(PREFIX_ITEM).get());
        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        DeliveryTime deliveryTime = ParserUtil.parseDeliveryTime(argMultimap.getValue(PREFIX_DATETIME).get());

        boolean isPast = !deliveryTime.isInFuture();

        Optional<Address> address = argMultimap.getValue(PREFIX_ADDRESS)
                .isPresent()
                ? Optional.of(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()))
                : Optional.empty();

        Optional<Status> status = argMultimap.getValue(PREFIX_STATUS)
                .isPresent()
                ? Optional.of(ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()))
                : Optional.empty();

        logger.log(Level.INFO, "Successfully parsed AddOrderCommand");

        return new AddOrderCommand(customerIndex, item, quantity, deliveryTime, address, status, isPast);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
