package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_INSTAGRAM = "amy_bee";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Instagram instagram;
    private Address address;
    private Remark remark;
    private Set<Tag> tags;
    private UUID id;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        instagram = new Instagram(DEFAULT_INSTAGRAM);
        address = new Address(DEFAULT_ADDRESS);
        remark = null;
        tags = new HashSet<>();
        id = UUID.randomUUID();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone().orElse(null);
        email = personToCopy.getEmail().orElse(null);
        instagram = personToCopy.getInstagram().orElse(null);
        address = personToCopy.getAddress().orElse(null);
        remark = personToCopy.getRemark().orElse(null);
        tags = new HashSet<>(personToCopy.getTags());
        id = personToCopy.getId();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Removes the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withoutAddress() {
        this.address = null;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Removes the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withoutPhone() {
        this.phone = null;
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Removes the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withoutEmail() {
        this.email = null;
        return this;
    }

    /**
     * Sets the {@code Instagram} of the {@code Person} that we are building.
     */
    public PersonBuilder withInstagram(String instagram) {
        this.instagram = new Instagram(instagram);
        return this;
    }

    /**
     * Removes the {@code Instagram} of the {@code Person} that we are building.
     */
    public PersonBuilder withoutInstagram() {
        this.instagram = null;
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Removes the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withoutRemark() {
        this.remark = null;
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, instagram, address, remark, tags);
    }

}
