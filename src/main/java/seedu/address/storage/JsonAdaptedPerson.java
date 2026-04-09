package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    public static final String INVALID_UUID_MESSAGE = "Invalid UUID for person's id: %s";

    private final String name;
    private final String phone;
    private final String facebook;
    private final String instagram;
    private final String address;
    private final String remark;
    private final String id;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("facebook") String facebook, @JsonProperty("instagram") String instagram,
            @JsonProperty("address") String address, @JsonProperty("remark") String remark,
            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("id") String id) {
        this.name = name;
        this.phone = phone;
        this.facebook = facebook;
        this.instagram = instagram;
        this.address = address;
        this.remark = remark;
        this.id = id;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().map(p -> p.value).orElse(null);
        facebook = source.getFacebook().map(fb -> fb.value).orElse(null);
        instagram = source.getInstagram().map(ig -> ig.value).orElse(null);
        address = source.getAddress().map(a -> a.value).orElse(null);
        remark = source.getRemark().map(r -> r.value).orElse(null);
        id = source.getId().toString();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone != null && !Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = phone != null ? new Phone(phone) : null;

        if (facebook != null && !Facebook.isValidFacebook(facebook)) {
            throw new IllegalValueException(Facebook.MESSAGE_CONSTRAINTS);
        }
        final Facebook modelFacebook = facebook != null ? new Facebook(facebook) : null;

        if (instagram != null && !Instagram.isValidInstagram(instagram)) {
            throw new IllegalValueException(Instagram.MESSAGE_CONSTRAINTS);
        }
        final Instagram modelInstagram = instagram != null ? new Instagram(instagram) : null;

        if (address != null && !Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = address != null ? new Address(address) : null;

        if (remark != null && !Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Remark.MESSAGE_CONSTRAINTS);
        }
        final Remark modelRemark = remark != null ? new Remark(remark) : null;

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final UUID modelId;
        if (id != null) {
            try {
                modelId = UUID.fromString(id);
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException(String.format(INVALID_UUID_MESSAGE, id));
            }
        } else {
            modelId = UUID.randomUUID();
        }

        return new Person(modelName, modelPhone, modelFacebook, modelInstagram, modelAddress, modelRemark, modelTags,
                modelId);
    }

}
