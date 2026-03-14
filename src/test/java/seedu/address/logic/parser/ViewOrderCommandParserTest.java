package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ViewOrderCommandParserTest {
    private ViewOrderCommandParser parser = new ViewOrderCommandParser();

    @Test
    public void parse_validPreparingStatus_returnsViewOrderCommand() throws ParseException {
        ViewOrderCommand result = parser.parse("preparing");

        assertNotNull(result);
    }

    @Test
    public void parse_mixedCaseStatus_returnsViewOrderCommand() throws ParseException {
        ViewOrderCommand result = parser.parse("ReAdY");

        assertNotNull(result);
    }
    @Test
    public void parse_uppercaseStatus_returnsViewOrderCommand() throws ParseException {
        ViewOrderCommand result = parser.parse("CANCELLED");

        assertNotNull(result);
    }

    // ===== Invalid Input Tests =====

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_onlyWhitespace_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("invalid"));
    }
}
