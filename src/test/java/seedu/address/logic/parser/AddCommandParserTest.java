package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_CONTACT_METHOD;
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
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACEBOOK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACEBOOK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTAGRAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTAGRAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.AMY_ADDRESS_ONLY;
import static seedu.address.testutil.TypicalPersons.AMY_FACEBOOK_ONLY;
import static seedu.address.testutil.TypicalPersons.AMY_INSTAGRAM_ONLY;
import static seedu.address.testutil.TypicalPersons.AMY_PHONE_ONLY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private static final String VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG = NAME_DESC_BOB + PHONE_DESC_BOB
            + FACEBOOK_DESC_BOB + INSTAGRAM_DESC_BOB + ADDRESS_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_FRIEND;

    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_unsupportedPrefixMergedIntoPhone_failure() {
        // Same spacing as after "add" from AddressBookParser: a leading space before the first prefix (see
        // ArgumentTokenizer). Unknown x/ is merged into the p/ value.
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + " n/John p/1234567 x/hello",
                String.format(Messages.MESSAGE_UNSUPPORTED_PREFIX, "x/"));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB)
                .withRemark(VALID_REMARK_BOB)
                .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB)
                .withRemark(VALID_REMARK_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG
                        + TAG_DESC_HUSBAND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        // multiple names
        assertDuplicatePrefixFailure(
                NAME_DESC_AMY + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG, PREFIX_NAME);

        // multiple phones
        assertDuplicatePrefixFailure(
                PHONE_DESC_AMY + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG, PREFIX_PHONE);

        // multiple facebook handles
        assertDuplicatePrefixFailure(
                FACEBOOK_DESC_AMY + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG, PREFIX_FACEBOOK);

        // multiple instagram handles
        assertDuplicatePrefixFailure(
                INSTAGRAM_DESC_AMY + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG, PREFIX_INSTAGRAM);

        // multiple addresses
        assertDuplicatePrefixFailure(
                ADDRESS_DESC_AMY + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG, PREFIX_ADDRESS);

        // multiple remarks
        assertDuplicatePrefixFailure(
                REMARK_DESC_AMY + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG, PREFIX_REMARK);

        // multiple fields repeated
        assertDuplicatePrefixFailure(
                VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG + PHONE_DESC_AMY + FACEBOOK_DESC_AMY
                        + NAME_DESC_AMY + INSTAGRAM_DESC_AMY + ADDRESS_DESC_AMY + REMARK_DESC_AMY
                        + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG,
                PREFIX_NAME, PREFIX_ADDRESS, PREFIX_FACEBOOK, PREFIX_INSTAGRAM, PREFIX_PHONE, PREFIX_REMARK);

        // invalid value followed by valid value

        // invalid name
        assertDuplicatePrefixFailure(
                INVALID_NAME_DESC + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG, PREFIX_NAME);

        // invalid facebook
        assertDuplicatePrefixFailure(
                INVALID_FACEBOOK_DESC + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG, PREFIX_FACEBOOK);

        // invalid phone
        assertDuplicatePrefixFailure(
                INVALID_PHONE_DESC + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG, PREFIX_PHONE);

        // invalid instagram
        assertDuplicatePrefixFailure(
                INVALID_INSTAGRAM_DESC + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG, PREFIX_INSTAGRAM);

        // invalid address
        assertDuplicatePrefixFailure(
                INVALID_ADDRESS_DESC + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG, PREFIX_ADDRESS);

        // invalid remark
        assertDuplicatePrefixFailure(
                INVALID_REMARK_DESC + VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG, PREFIX_REMARK);

        // valid value followed by invalid value

        // invalid name
        assertDuplicatePrefixFailure(
                VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG + INVALID_NAME_DESC, PREFIX_NAME);

        // invalid facebook
        assertDuplicatePrefixFailure(
                VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG + INVALID_FACEBOOK_DESC, PREFIX_FACEBOOK);

        // invalid phone
        assertDuplicatePrefixFailure(
                VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG + INVALID_PHONE_DESC, PREFIX_PHONE);

        // invalid instagram
        assertDuplicatePrefixFailure(
                VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG + INVALID_INSTAGRAM_DESC, PREFIX_INSTAGRAM);

        // invalid address
        assertDuplicatePrefixFailure(
                VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG + INVALID_ADDRESS_DESC, PREFIX_ADDRESS);

        // invalid remark
        assertDuplicatePrefixFailure(
                VALID_PERSON_INPUT_BOB_WITH_ALL_FIELDS_AND_ONE_TAG + INVALID_REMARK_DESC, PREFIX_REMARK);
    }

    @Test
    public void parse_someOptionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withRemark(VALID_REMARK_AMY).withTags().build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + FACEBOOK_DESC_AMY + INSTAGRAM_DESC_AMY
                        + ADDRESS_DESC_AMY + REMARK_DESC_AMY,
                new AddCommand(expectedPerson));

        // only name and phone
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY, new AddCommand(AMY_PHONE_ONLY));

        // only name and facebook
        assertParseSuccess(parser, NAME_DESC_AMY + FACEBOOK_DESC_AMY, new AddCommand(AMY_FACEBOOK_ONLY));

        // only name and instagram
        assertParseSuccess(parser, NAME_DESC_AMY + INSTAGRAM_DESC_AMY, new AddCommand(AMY_INSTAGRAM_ONLY));

        // only name, phone and address (1 contact method)
        Person expectedPersonWithPhoneAddress =
                new PersonBuilder(AMY_PHONE_ONLY).withAddress(VALID_ADDRESS_AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedPersonWithPhoneAddress));

        // only name, phone and remark (1 contact method)
        Person expectedPersonWithPhoneRemark = new PersonBuilder(AMY_PHONE_ONLY).withRemark(VALID_REMARK_AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + REMARK_DESC_AMY,
                new AddCommand(expectedPersonWithPhoneRemark));

        // only name, phone and facebook (2 contact methods)
        Person expectedPersonWithPhoneFb =
                new PersonBuilder(AMY_PHONE_ONLY).withFacebook(VALID_FACEBOOK_AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + FACEBOOK_DESC_AMY,
                new AddCommand(expectedPersonWithPhoneFb));

        // only name, phone and instagram (2 contact methods)
        Person expectedPersonWithPhoneIg = new PersonBuilder(AMY_PHONE_ONLY).withInstagram(VALID_INSTAGRAM_AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + INSTAGRAM_DESC_AMY,
                new AddCommand(expectedPersonWithPhoneIg));

        // only name, facebook and address (1 contact method)
        Person expectedPersonWithFbAddress =
                new PersonBuilder(AMY_FACEBOOK_ONLY).withAddress(VALID_ADDRESS_AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + FACEBOOK_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedPersonWithFbAddress));

        // only name, facebook and remark (1 contact method)
        Person expectedPersonWithFbRemark = new PersonBuilder(AMY_FACEBOOK_ONLY).withRemark(VALID_REMARK_AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + FACEBOOK_DESC_AMY + REMARK_DESC_AMY,
                new AddCommand(expectedPersonWithFbRemark));

        // only name, facebook and instagram (2 contact methods)
        Person expectedPersonWithFbIg =
                new PersonBuilder(AMY_FACEBOOK_ONLY).withInstagram(VALID_INSTAGRAM_AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + FACEBOOK_DESC_AMY + INSTAGRAM_DESC_AMY,
                new AddCommand(expectedPersonWithFbIg));

        // only name, instagram and address (1 contact method)
        Person expectedPersonWithAddressIg =
                new PersonBuilder(AMY_ADDRESS_ONLY).withInstagram(VALID_INSTAGRAM_AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + ADDRESS_DESC_AMY + INSTAGRAM_DESC_AMY,
                new AddCommand(expectedPersonWithAddressIg));

        // only name, instagram and remark (1 contact method)
        Person expectedPersonWithIgRemark = new PersonBuilder(AMY_INSTAGRAM_ONLY).withRemark(VALID_REMARK_AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + INSTAGRAM_DESC_AMY + REMARK_DESC_AMY,
                new AddCommand(expectedPersonWithIgRemark));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + FACEBOOK_DESC_BOB
                        + INSTAGRAM_DESC_BOB + ADDRESS_DESC_BOB + REMARK_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_FACEBOOK_BOB
                        + VALID_INSTAGRAM_BOB + VALID_ADDRESS_BOB + VALID_REMARK_BOB,
                expectedMessage);
    }

    @Test
    public void parse_missingContactMethod_failure() {
        String expectedMessage = MESSAGE_MISSING_CONTACT_METHOD;

        // only name provided (no contact methods)
        assertParseFailure(parser, NAME_DESC_AMY, expectedMessage);

        // name with tag but no contact methods
        assertParseFailure(parser, NAME_DESC_AMY + TAG_DESC_FRIEND, expectedMessage);

        // name with remark but no contact methods
        assertParseFailure(parser, NAME_DESC_AMY + REMARK_DESC_AMY, expectedMessage);

        // only name and address (address is not a contact method)
        assertParseFailure(parser, NAME_DESC_AMY + ADDRESS_DESC_AMY, expectedMessage);

        // only name, address and remark (address is not a contact method)
        assertParseFailure(parser, NAME_DESC_AMY + ADDRESS_DESC_AMY + REMARK_DESC_AMY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + FACEBOOK_DESC_BOB + INSTAGRAM_DESC_BOB
                + ADDRESS_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + FACEBOOK_DESC_BOB + INSTAGRAM_DESC_BOB
                + ADDRESS_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid facebook
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_FACEBOOK_DESC + INSTAGRAM_DESC_BOB
                + ADDRESS_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Facebook.MESSAGE_CONSTRAINTS);

        // invalid instagram
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + FACEBOOK_DESC_BOB + INVALID_INSTAGRAM_DESC
                + ADDRESS_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Instagram.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + FACEBOOK_DESC_BOB + INSTAGRAM_DESC_BOB
                + INVALID_ADDRESS_DESC + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + FACEBOOK_DESC_BOB + INSTAGRAM_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_REMARK_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Remark.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + FACEBOOK_DESC_BOB + INSTAGRAM_DESC_BOB
                + ADDRESS_DESC_BOB + REMARK_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + FACEBOOK_DESC_BOB
                        + INSTAGRAM_DESC_BOB + INVALID_ADDRESS_DESC + REMARK_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + FACEBOOK_DESC_BOB
                        + ADDRESS_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    private void assertDuplicatePrefixFailure(String input, Prefix... repeatedPrefixes) {
        assertParseFailure(parser, input, Messages.getErrorMessageForDuplicatePrefixes(repeatedPrefixes));
    }
}
