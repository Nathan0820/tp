package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;

public class ClearCommandParserTest {

    private final ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_emptyArgs_returnsUnconfirmedClearCommand() {
        assertParseSuccess(parser, "", new ClearCommand(false));
        assertParseSuccess(parser, "   ", new ClearCommand(false));
    }

    @Test
    public void parse_confirmArg_returnsConfirmedClearCommand() {
        assertParseSuccess(parser, "CONFIRM", new ClearCommand(true));
        assertParseSuccess(parser, "   CONFIRM   ", new ClearCommand(true));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "c",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "anything",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "confirm",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
    }
}
