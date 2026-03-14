package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindOrderCommand;
import seedu.address.model.order.OrderContainsKeywordsPredicate;

public class FindOrderCommandParserTest {

    private final FindOrderCommandParser parser = new FindOrderCommandParser();

    @Test
    public void parse_itemPrefix_returnsFindOrderCommand() {
        assertParseSuccess(parser, " i/pizza",
                new FindOrderCommand(
                        new OrderContainsKeywordsPredicate(
                                OrderContainsKeywordsPredicate.SearchType.ITEM, "pizza")));
    }

    @Test
    public void parse_addressPrefix_returnsFindOrderCommand() {
        assertParseSuccess(parser, " a/Clementi",
                new FindOrderCommand(
                        new OrderContainsKeywordsPredicate(
                                OrderContainsKeywordsPredicate.SearchType.ADDRESS, "Clementi")));
    }

    @Test
    public void parse_customerPrefix_returnsFindOrderCommand() {
        assertParseSuccess(parser, " c/1",
                new FindOrderCommand(
                        new OrderContainsKeywordsPredicate(
                                OrderContainsKeywordsPredicate.SearchType.CUSTOMER, "1")));
    }

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "pizza",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        assertParseFailure(parser, " i/pizza a/Clementi",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
    }
}
