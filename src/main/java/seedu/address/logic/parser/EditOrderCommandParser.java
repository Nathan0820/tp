package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditOrderCommand;
import seedu.address.logic.commands.EditOrderCommand.EditOrderDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditOrderCommand object
 */
public class EditOrderCommandParser implements Parser<EditOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditOrderCommand
     * and returns an EditOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(
                    args,
                    PREFIX_ITEM,
                    PREFIX_QUANTITY,
                    PREFIX_DATETIME,
                    PREFIX_ADDRESS,
                    PREFIX_STATUS
                );

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_ITEM, PREFIX_QUANTITY, PREFIX_DATETIME, PREFIX_ADDRESS, PREFIX_STATUS);

        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();

        if (argMultimap.getValue(PREFIX_ITEM).isPresent()) {
            editOrderDescriptor.setItem(
                ParserUtil.parseItem(
                    argMultimap.getValue(PREFIX_ITEM).get()
                )
            );
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            editOrderDescriptor.setQuantity(
                ParserUtil.parseQuantity(
                    argMultimap.getValue(PREFIX_QUANTITY).get()
                )
            );
        }
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editOrderDescriptor.setDeliveryTime(
                ParserUtil.parseDeliveryTime(
                    argMultimap.getValue(PREFIX_DATETIME).get()
                )
            );
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editOrderDescriptor.setAddress(
                ParserUtil.parseAddress(
                    argMultimap.getValue(PREFIX_ADDRESS).get()
                )
            );
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            editOrderDescriptor.setStatus(
                ParserUtil.parseStatus(
                    argMultimap.getValue(PREFIX_STATUS).get()
                )
            );
        }
        if (!editOrderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditOrderCommand.MESSAGE_NOT_EDITED);
        }

        return new EditOrderCommand(index, editOrderDescriptor);
    }

}
