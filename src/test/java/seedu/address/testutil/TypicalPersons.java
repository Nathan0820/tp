package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACEBOOK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACEBOOK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTAGRAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTAGRAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withFacebook("alice.pauline")
            .withInstagram("alicee").withPhone("94351253")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withFacebook("benson.meier").withPhone("98765432").withInstagram("benson")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withoutInstagram().withFacebook("carl.kurz").withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withFacebook("daniel.meier").withoutInstagram()
            .withAddress("10th street").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822242")
            .withInstagram("elles_meyer").withFacebook("elle.meyer").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94824272")
            .withoutInstagram().withFacebook("fiona.kunz").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94824422")
            .withInstagram("GeorgeBest").withFacebook("george.best").withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84824242")
            .withFacebook("hoon.meier").withoutInstagram().withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84821313")
            .withoutFacebook().withInstagram("ida.mueller").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withFacebook(VALID_FACEBOOK_AMY).withInstagram(VALID_INSTAGRAM_AMY)
            .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withFacebook(VALID_FACEBOOK_BOB).withAddress(VALID_ADDRESS_BOB).withInstagram(VALID_INSTAGRAM_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    // Manually added - Persons with partial contact details (for testing optional fields)
    public static final Person AMY_PHONE_ONLY =
            new PersonBuilder(AMY).withoutFacebook().withoutInstagram().withoutAddress().withTags().build();

    public static final Person AMY_FACEBOOK_ONLY =
            new PersonBuilder(AMY).withoutPhone().withoutInstagram().withoutAddress().withTags().build();

    public static final Person AMY_INSTAGRAM_ONLY =
            new PersonBuilder(AMY).withoutPhone().withoutFacebook().withoutAddress().withTags().build();

    public static final Person AMY_ADDRESS_ONLY =
            new PersonBuilder(AMY).withoutPhone().withoutFacebook().withoutInstagram().withTags().build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
