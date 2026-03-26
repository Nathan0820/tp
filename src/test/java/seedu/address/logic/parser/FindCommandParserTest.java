package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    private Map<PersonContainsKeywordsPredicate.SearchType, String> createMap(
            PersonContainsKeywordsPredicate.SearchType type, String value) {
        Map<PersonContainsKeywordsPredicate.SearchType, String> map = new HashMap<>();
        map.put(type, value);
        return map;
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        "Alex Yeoh", true, new HashMap<>()));
        assertParseSuccess(parser, "Alex Yeoh", expectedFindCommand);

        // leading and trailing whitespaces are trimmed
        assertParseSuccess(parser, "  Alex Yeoh  ", expectedFindCommand);
    }

    @Test
    public void parse_validSpecificName_returnsFindCommand() {
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate("n/Alice",
                        false, createMap(
                                PersonContainsKeywordsPredicate.SearchType.NAME, "Alice"));
        FindCommand expectedCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, " n/Alice", expectedCommand);
    }

    @Test
    public void parse_validSpecificPhone_returnsFindCommand() {
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate("p/98765432",
                        false, createMap(
                                PersonContainsKeywordsPredicate.SearchType.PHONE, "98765432"));
        FindCommand expectedCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, " p/98765432", expectedCommand);
    }

    @Test
    public void parse_validSpecificAddress_returnsFindCommand() {
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate("a/Kent Ridge",
                        false, createMap(
                                PersonContainsKeywordsPredicate.SearchType.ADDRESS, "Kent Ridge"));
        FindCommand expectedCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, " a/Kent Ridge", expectedCommand);
    }

    @Test
    public void parse_validSpecificTag_returnsFindCommand() {
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate("t/VIP",
                        false, createMap(
                                PersonContainsKeywordsPredicate.SearchType.TAG, "VIP"));
        FindCommand expectedCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, " t/VIP", expectedCommand);
    }

    @Test
    public void parse_validSpecificFacebook_returnsFindCommand() {
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate("fb/Alice_hhh",
                        false, createMap(
                                PersonContainsKeywordsPredicate.SearchType.FACEBOOK, "Alice_hhh"));
        FindCommand expectedCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, " fb/Alice_hhh", expectedCommand);
    }

    @Test
    public void parse_validSpecificIg_returnsFindCommand() {
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate("ig/Alice_hhh",
                        false, createMap(
                                PersonContainsKeywordsPredicate.SearchType.INSTAGRAM, "Alice_hhh"));
        FindCommand expectedCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, " ig/Alice_hhh", expectedCommand);
    }

    @Test
    public void parse_validSpecificRemark_returnsFindCommand() {
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate("r/non-spicy",
                        false, createMap(
                                PersonContainsKeywordsPredicate.SearchType.REMARK, "non-spicy"));
        FindCommand expectedCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, " r/non-spicy", expectedCommand);
    }

    @Test
    public void parse_unknownPrefix_treatedAsKeywords() {
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate("z/Unknown", true, new HashMap<>());
        FindCommand expectedCommand = new FindCommand(expectedPredicate);
        assertParseSuccess(parser, " z/Unknown", expectedCommand);
    }

}
