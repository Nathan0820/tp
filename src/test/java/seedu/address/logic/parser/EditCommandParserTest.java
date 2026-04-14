package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FACEBOOK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FACEBOOK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INSTAGRAM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INSTAGRAM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FACEBOOK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INSTAGRAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACEBOOK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTAGRAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTAGRAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;
    private static final String PHONE_EMPTY = " " + PREFIX_PHONE;
    private static final String FACEBOOK_EMPTY = " " + PREFIX_FACEBOOK;
    private static final String INSTAGRAM_EMPTY = " " + PREFIX_INSTAGRAM;
    private static final String ADDRESS_EMPTY = " " + PREFIX_ADDRESS;
    private static final String REMARK_EMPTY = " " + PREFIX_REMARK;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_unsupportedPrefixInPhoneValue_failure() {
        assertParseFailure(parser,
                "1 p/1234567 x/hello",
                String.format(Messages.MESSAGE_UNSUPPORTED_PREFIX, "x/"));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_FACEBOOK_DESC, Facebook.MESSAGE_CONSTRAINTS); // invalid facebook
        assertParseFailure(parser, "1" + INVALID_INSTAGRAM_DESC, Instagram.MESSAGE_CONSTRAINTS); // invalid instagram
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid Facebook
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + FACEBOOK_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_FACEBOOK_DESC
                        + VALID_ADDRESS_AMY + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + FACEBOOK_DESC_AMY + INSTAGRAM_DESC_BOB + ADDRESS_DESC_AMY + REMARK_DESC_AMY
                + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withFacebook(VALID_FACEBOOK_AMY).withInstagram(VALID_INSTAGRAM_BOB)
                .withAddress(VALID_ADDRESS_AMY).withRemark(VALID_REMARK_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INSTAGRAM_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withInstagram(VALID_INSTAGRAM_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_PERSON;

        // name
        assertParsesToDescriptor(targetIndex, NAME_DESC_AMY,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build());

        // phone
        assertParsesToDescriptor(targetIndex, PHONE_DESC_AMY,
                new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build());

        // facebook
        assertParsesToDescriptor(targetIndex, FACEBOOK_DESC_AMY,
                new EditPersonDescriptorBuilder().withFacebook(VALID_FACEBOOK_AMY).build());

        // instagram
        assertParsesToDescriptor(targetIndex, INSTAGRAM_DESC_AMY,
                new EditPersonDescriptorBuilder().withInstagram(VALID_INSTAGRAM_AMY).build());

        // address
        assertParsesToDescriptor(targetIndex, ADDRESS_DESC_AMY,
                new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build());

        // remark
        assertParsesToDescriptor(targetIndex, REMARK_DESC_AMY,
                new EditPersonDescriptorBuilder().withRemark(VALID_REMARK_AMY).build());

        // tags
        assertParsesToDescriptor(targetIndex, TAG_DESC_FRIEND,
                new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build());
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // invalid followed by valid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // valid followed by invalid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + FACEBOOK_DESC_AMY
                + INSTAGRAM_DESC_AMY + REMARK_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + FACEBOOK_DESC_AMY + INSTAGRAM_DESC_AMY + REMARK_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + FACEBOOK_DESC_BOB + INSTAGRAM_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_FACEBOOK,
                        PREFIX_INSTAGRAM, PREFIX_ADDRESS, PREFIX_REMARK));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_FACEBOOK_DESC
                + INVALID_INSTAGRAM_DESC + INVALID_REMARK_DESC + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC
                + INVALID_FACEBOOK_DESC + INVALID_INSTAGRAM_DESC + INVALID_REMARK_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_FACEBOOK,
                        PREFIX_INSTAGRAM, PREFIX_ADDRESS, PREFIX_REMARK));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetOptionalSingleValueFields_success() {
        Index targetIndex = INDEX_THIRD_PERSON;

        assertParsesToDescriptor(targetIndex, PHONE_EMPTY,
                new EditPersonDescriptorBuilder().clearPhone().build());
        assertParsesToDescriptor(targetIndex, FACEBOOK_EMPTY,
                new EditPersonDescriptorBuilder().clearFacebook().build());
        assertParsesToDescriptor(targetIndex, INSTAGRAM_EMPTY,
                new EditPersonDescriptorBuilder().clearInstagram().build());
        assertParsesToDescriptor(targetIndex, ADDRESS_EMPTY,
                new EditPersonDescriptorBuilder().clearAddress().build());
        assertParsesToDescriptor(targetIndex, REMARK_EMPTY,
                new EditPersonDescriptorBuilder().clearRemark().build());
    }

    private void assertParsesToDescriptor(Index targetIndex, String fieldInput, EditPersonDescriptor descriptor) {
        String userInput = targetIndex.getOneBased() + fieldInput;
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
