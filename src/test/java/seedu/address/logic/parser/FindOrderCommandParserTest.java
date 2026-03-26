package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindOrderCommand;
import seedu.address.model.order.OrderContainsKeywordsPredicate;
import seedu.address.model.order.Status;

public class FindOrderCommandParserTest {

    private final FindOrderCommandParser parser = new FindOrderCommandParser();

    private Map<OrderContainsKeywordsPredicate.SearchType, String> createMap(
            OrderContainsKeywordsPredicate.SearchType type, String value) {
        Map<OrderContainsKeywordsPredicate.SearchType, String> map = new HashMap<>();
        map.put(type, value);
        return map;
    }
    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " x/value",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_itemPrefix_returnsFindOrderCommand() {
        assertParseSuccess(parser, " i/pizza",
                new FindOrderCommand(
                        new OrderContainsKeywordsPredicate(createMap(
                                OrderContainsKeywordsPredicate.SearchType.ITEM, "pizza"))));
    }

    @Test
    public void parse_addressPrefix_returnsFindOrderCommand() {
        assertParseSuccess(parser, " a/Clementi",
                new FindOrderCommand(
                        new OrderContainsKeywordsPredicate(createMap(
                                OrderContainsKeywordsPredicate.SearchType.ADDRESS, "Clementi"))));
    }

    @Test
    public void parse_customerPrefix_returnsFindOrderCommand() {
        assertParseSuccess(parser, " c/1",
                new FindOrderCommand(
                        new OrderContainsKeywordsPredicate(createMap(
                                OrderContainsKeywordsPredicate.SearchType.CUSTOMER, "1"))));
    }

    @Test
    public void parse_statusPrefix_returnsFindOrderCommand() {
        assertParseSuccess(parser, " s/PREPARING",
                new FindOrderCommand(
                        new OrderContainsKeywordsPredicate(createMap(
                                OrderContainsKeywordsPredicate.SearchType.STATUS, "PREPARING"))));
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        assertParseFailure(parser, " s/unknown",
                String.format(Status.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_multiplePrefixes_returnsFindOrderCommand() {
        Map<OrderContainsKeywordsPredicate.SearchType, String> expectedMap = new HashMap<>();
        expectedMap.put(OrderContainsKeywordsPredicate.SearchType.ITEM, "pizza");
        expectedMap.put(OrderContainsKeywordsPredicate.SearchType.ADDRESS, "Clementi");
        OrderContainsKeywordsPredicate expectedPredicate =
                new OrderContainsKeywordsPredicate(expectedMap);
        assertParseSuccess(parser, " i/pizza a/Clementi",
                new FindOrderCommand(expectedPredicate));
    }

    @Test
    public void parse_emptyPrefixValue_throwsParseException() {
        assertParseFailure(parser, " i/ ", "Item search value cannot be empty.");
        assertParseFailure(parser, " s/ ", "Status search value cannot be empty.");
        assertParseFailure(parser, " a/ ", "Address search value cannot be empty.");
    }
}
