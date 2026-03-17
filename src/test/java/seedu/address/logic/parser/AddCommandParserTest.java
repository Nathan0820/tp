package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_CONTACT_METHOD;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INSTAGRAM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INSTAGRAM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INSTAGRAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTAGRAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTAGRAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.AMY_ADDRESS_ONLY;
import static seedu.address.testutil.TypicalPersons.AMY_EMAIL_ONLY;
import static seedu.address.testutil.TypicalPersons.AMY_INSTAGRAM_ONLY;
import static seedu.address.testutil.TypicalPersons.AMY_PHONE_ONLY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INSTAGRAM_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INSTAGRAM_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INSTAGRAM_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple instagram handles
        assertParseFailure(parser, INSTAGRAM_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INSTAGRAM));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + INSTAGRAM_DESC_AMY + ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(
                        PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_INSTAGRAM, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid instagram
        assertParseFailure(parser, INVALID_INSTAGRAM_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INSTAGRAM));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid instagram
        assertParseFailure(parser, validExpectedPersonString + INVALID_INSTAGRAM_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INSTAGRAM));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INSTAGRAM_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));

        // only name and phone (no email, instagram and address)
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY, new AddCommand(AMY_PHONE_ONLY));

        // only name and email (no phone, instagram and address)
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY, new AddCommand(AMY_EMAIL_ONLY));

        // only name and instagram (no phone, email and address)
        assertParseSuccess(parser, NAME_DESC_AMY + INSTAGRAM_DESC_AMY, new AddCommand(AMY_INSTAGRAM_ONLY));

        // only name and address (no phone, instagram and email)
        assertParseSuccess(parser, NAME_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(AMY_ADDRESS_ONLY));

        // only name, phone and email
        Person expectedPersonWithPhoneEmail = new PersonBuilder(AMY_PHONE_ONLY).withEmail(VALID_EMAIL_AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY, new AddCommand(expectedPersonWithPhoneEmail));

        // only name, phone and address
        Person expectedPersonWithPhoneAddress =
                new PersonBuilder(AMY_PHONE_ONLY).withAddress(VALID_ADDRESS_AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedPersonWithPhoneAddress));

        // only name, email and address
        Person expectedPersonWithEmailAddress =
                new PersonBuilder(AMY_EMAIL_ONLY).withAddress(VALID_ADDRESS_AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedPersonWithEmailAddress));

        // only name, phone and instagram
        Person expectedPersonWithPhoneIg = new PersonBuilder(AMY_PHONE_ONLY).withInstagram(VALID_INSTAGRAM_AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + INSTAGRAM_DESC_AMY,
                new AddCommand(expectedPersonWithPhoneIg));

        // only name, email and instagram
        Person expectedPersonWithEmailIg = new PersonBuilder(AMY_EMAIL_ONLY).withInstagram(VALID_INSTAGRAM_AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + EMAIL_DESC_AMY + INSTAGRAM_DESC_AMY,
                new AddCommand(expectedPersonWithEmailIg));

        // only name, address and instagram
        Person expectedPersonWithAddressIg =
                new PersonBuilder(AMY_ADDRESS_ONLY).withInstagram(VALID_INSTAGRAM_AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + ADDRESS_DESC_AMY + INSTAGRAM_DESC_AMY,
                new AddCommand(expectedPersonWithAddressIg));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INSTAGRAM_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                        + VALID_INSTAGRAM_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_missingContactMethod_failure() {
        String expectedMessage = MESSAGE_MISSING_CONTACT_METHOD;

        // only name provided (no contact methods)
        assertParseFailure(parser, NAME_DESC_AMY, expectedMessage);

        // name with tag but no contact methods
        assertParseFailure(parser, NAME_DESC_AMY + TAG_DESC_FRIEND, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INSTAGRAM_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + INSTAGRAM_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + INSTAGRAM_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid instagram
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_INSTAGRAM_DESC
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Instagram.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INSTAGRAM_DESC_BOB
                + INVALID_ADDRESS_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INSTAGRAM_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INSTAGRAM_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
