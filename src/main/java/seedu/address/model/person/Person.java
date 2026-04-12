package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: name and tags are present and not null, field values are validated, immutable.
 * <p>
 * Phone, Facebook, and Instagram are optional and may be null, but at least one of them must be present.
 * Address and Remark are optional and may be null.
 */
public class Person {

    public static final String MATCH_FIELD_PHONE = "phone";
    public static final String MATCH_FIELD_FACEBOOK = "Facebook";
    public static final String MATCH_FIELD_INSTAGRAM = "Instagram";

    // Identity fields
    private final Name name;
    private final Phone phone; // can be null
    private final Facebook facebook; // can be null
    private final Instagram instagram; // can be null

    // Data fields
    private final Address address; // can be null
    private final Remark remark; // can be null
    private final Set<Tag> tags = new HashSet<>();
    private final UUID id;

    /**
     * Name and tags must be present and not null.
     * At least one of phone, Facebook, or Instagram must be present (not null).
     * Address and Remark can be null.
     */
    public Person(Name name, Phone phone, Facebook facebook, Instagram instagram, Address address, Remark remark,
                  Set<Tag> tags) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.phone = phone;
        this.facebook = facebook;
        this.instagram = instagram;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
        this.id = UUID.randomUUID();
    }

    /**
     * Overloaded constructor that accepts a specific UUID.
     * Used when loading from storage to preserve the original UUID.
     */
    public Person(Name name, Phone phone, Facebook facebook, Instagram instagram, Address address, Remark remark,
                  Set<Tag> tags, UUID id) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.phone = phone;
        this.facebook = facebook;
        this.instagram = instagram;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
        this.id = id != null ? id : UUID.randomUUID();
    }


    public Name getName() {
        return name;
    }

    public Optional<Phone> getPhone() {
        return Optional.ofNullable(phone);
    }

    public Optional<Facebook> getFacebook() {
        return Optional.ofNullable(facebook);
    }

    public Optional<Instagram> getInstagram() {
        return Optional.ofNullable(instagram);
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    public Optional<Remark> getRemark() {
        return Optional.ofNullable(remark);
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public UUID getId() {
        return id;
    }

    /**
     * Returns the contact fields (excluding address field) that are present in both persons and have identical values.
     */
    public Set<String> getMatchingContactFields(Person otherPerson) {
        requireAllNonNull(otherPerson);

        Set<String> matchingFields = new LinkedHashSet<>();
        if (hasSameNonNullValue(phone, otherPerson.phone)) {
            matchingFields.add(MATCH_FIELD_PHONE);
        }
        if (hasSameNonNullValue(facebook, otherPerson.facebook)) {
            matchingFields.add(MATCH_FIELD_FACEBOOK);
        }
        if (hasSameNonNullValue(instagram, otherPerson.instagram)) {
            matchingFields.add(MATCH_FIELD_INSTAGRAM);
        }

        return Collections.unmodifiableSet(matchingFields);
    }

    private static <T> boolean hasSameNonNullValue(T first, T second) {
        return first != null && first.equals(second);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(facebook, otherPerson.facebook)
                && Objects.equals(instagram, otherPerson.instagram)
                && Objects.equals(address, otherPerson.address)
                && Objects.equals(remark, otherPerson.remark)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, facebook, instagram, address, remark, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("facebook", facebook)
                .add("instagram", instagram)
                .add("address", address)
                .add("remark", remark)
                .add("tags", tags)
                .toString();
    }

}
