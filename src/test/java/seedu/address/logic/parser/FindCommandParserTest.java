package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate("Alex Yeoh"));
        assertParseSuccess(parser, "Alex Yeoh", expectedFindCommand);

        // leading and trailing whitespaces are trimmed
        assertParseSuccess(parser, "  Alex Yeoh  ", expectedFindCommand);
    }

    @Test
    public void parse_validSpecificArgs_returnsFindCommand() {
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate("Alice",
                        PersonContainsKeywordsPredicate.SearchType.NAME);
        FindCommand expectedCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, " n/Alice", expectedCommand);
    }

    @Test
    public void parse_unknownPrefix_treatedAsKeywords() {
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate("z/Unknown");
        FindCommand expectedCommand = new FindCommand(expectedPredicate);
        assertParseSuccess(parser, " z/Unknown", expectedCommand);
    }

}
