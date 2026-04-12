package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.person.Person.MATCH_FIELD_FACEBOOK;
import static seedu.address.model.person.Person.MATCH_FIELD_INSTAGRAM;
import static seedu.address.model.person.Person.MATCH_FIELD_PHONE;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MessagesTest {

    private static final String PHONE = "91234567";
    private static final String FACEBOOK = "john.doe";
    private static final String INSTAGRAM = "john_doe";

    private static Person personWith(String phone, String facebook, String instagram) {
        return new PersonBuilder()
                .withName("Test Person")
                .withPhone(phone)
                .withFacebook(facebook)
                .withInstagram(instagram)
                .withoutAddress()
                .build();
    }

    @Test
    public void formatDuplicateContactWarning_emptyFields_usesFallbackMessage() {
        Person person = personWith(PHONE, FACEBOOK, INSTAGRAM);

        assertEquals(String.format(Messages.MESSAGE_WARNING_DUPLICATE_CONTACT, "",
                        "Run find with the matching value(s) to review."),
                Messages.formatDuplicateContactWarning(Set.of(), person));
    }

    @Test
    public void formatDuplicateContactWarning_phoneField_includesPhoneExample() {
        Person person = personWith(PHONE, FACEBOOK, INSTAGRAM);

        assertEquals(String.format(Messages.MESSAGE_WARNING_DUPLICATE_CONTACT, MATCH_FIELD_PHONE,
                        "Try: find " + PREFIX_PHONE + PHONE),
                Messages.formatDuplicateContactWarning(Set.of(MATCH_FIELD_PHONE), person));
    }

    @Test
    public void formatDuplicateContactWarning_facebookField_includesFacebookExample() {
        Person person = personWith(PHONE, FACEBOOK, INSTAGRAM);

        assertEquals(String.format(Messages.MESSAGE_WARNING_DUPLICATE_CONTACT, MATCH_FIELD_FACEBOOK,
                        "Try: find " + PREFIX_FACEBOOK + FACEBOOK),
                Messages.formatDuplicateContactWarning(Set.of(MATCH_FIELD_FACEBOOK), person));
    }

    @Test
    public void formatDuplicateContactWarning_instagramField_includesInstagramExample() {
        Person person = personWith(PHONE, FACEBOOK, INSTAGRAM);

        assertEquals(String.format(Messages.MESSAGE_WARNING_DUPLICATE_CONTACT, MATCH_FIELD_INSTAGRAM,
                        "Try: find " + PREFIX_INSTAGRAM + INSTAGRAM),
                Messages.formatDuplicateContactWarning(Set.of(MATCH_FIELD_INSTAGRAM), person));
    }

    @Test
    public void formatDuplicateContactWarning_multipleFields_includesAllExamples() {
        Person person = personWith(PHONE, FACEBOOK, INSTAGRAM);
        Set<String> fields = new LinkedHashSet<>();
        fields.add(MATCH_FIELD_PHONE);
        fields.add(MATCH_FIELD_FACEBOOK);

        assertEquals(String.format(Messages.MESSAGE_WARNING_DUPLICATE_CONTACT,
                        MATCH_FIELD_PHONE + ", " + MATCH_FIELD_FACEBOOK,
                        "Try: find " + PREFIX_PHONE + PHONE + " or find " + PREFIX_FACEBOOK + FACEBOOK),
                Messages.formatDuplicateContactWarning(fields, person));
    }
}

