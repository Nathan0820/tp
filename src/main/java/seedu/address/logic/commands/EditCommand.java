package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_FACEBOOK + "FACEBOOK] "
            + "[" + PREFIX_INSTAGRAM + "INSTAGRAM] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_INSTAGRAM + "john_doe";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private static final Logger logger = Logger.getLogger(EditCommand.class.getName());

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.warning("Invalid index provided: " + index.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!hasAtLeastOneContactMethod(editedPerson)) {
            logger.warning("Attempted to remove all contact methods of edited customer: " + editedPerson);
            throw new CommandException(Messages.MESSAGE_MISSING_CONTACT_METHOD);
        }

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            logger.warning("Duplicate customer rejected: same name already exists ("
                    + editedPerson.getName() + ")");
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        logger.info("Editing customer: " + personToEdit + " to " + editedPerson);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        logger.info("Successfully edited customer: " + editedPerson);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;
        assert editPersonDescriptor != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = resolveEditableOptionalField(editPersonDescriptor.phoneUpdate, personToEdit.getPhone());
        Facebook updatedFacebook = resolveEditableOptionalField(editPersonDescriptor.facebookUpdate,
                personToEdit.getFacebook());
        Instagram updatedInstagram = resolveEditableOptionalField(editPersonDescriptor.instagramUpdate,
                personToEdit.getInstagram());
        Address updatedAddress = resolveEditableOptionalField(editPersonDescriptor.addressUpdate,
                personToEdit.getAddress());
        Remark updatedRemark = resolveEditableOptionalField(editPersonDescriptor.remarkUpdate,
                personToEdit.getRemark());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        assert updatedName != null;
        assert updatedTags != null;

        return new Person(updatedName, updatedPhone, updatedFacebook, updatedInstagram, updatedAddress,
                updatedRemark, updatedTags);
    }

    /**
     * Resolves an optional field that supports explicit clearing in edit.
     * If the field is edited, returns the descriptor value (which may be empty and thus resolves to null).
     * If the field is not edited, falls back to the original value.
     */
    private static <T> T resolveEditableOptionalField(EditPersonDescriptor.FieldUpdate<T> fieldUpdate,
                                                      Optional<T> originalField) {
        assert fieldUpdate != null;
        assert originalField != null;
        return fieldUpdate.resolveAgainst(originalField.orElse(null));
    }

    /** Returns true if the person has at least one contact method. */
    private static boolean hasAtLeastOneContactMethod(Person person) {
        assert person != null;
        return person.getPhone().isPresent()
                || person.getFacebook().isPresent()
                || person.getInstagram().isPresent()
                || person.getAddress().isPresent();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private static final class FieldUpdate<T> {
            private enum State { UNCHANGED, CLEAR, SET }

            private final State state;
            private final T value;

            private FieldUpdate(State state, T value) {
                assert state != null;
                assert state != State.SET || value != null;
                this.state = state;
                this.value = value;
            }

            static <T> FieldUpdate<T> unchanged() {
                return new FieldUpdate<>(State.UNCHANGED, null);
            }

            static <T> FieldUpdate<T> clear() {
                return new FieldUpdate<>(State.CLEAR, null);
            }

            static <T> FieldUpdate<T> set(T value) {
                // Preserve existing behavior where set(null) is treated as an explicit clear
                return value == null ? clear() : new FieldUpdate<>(State.SET, value);
            }

            boolean isEdited() {
                return state != State.UNCHANGED;
            }

            Optional<T> getEditedValue() {
                return state == State.SET ? Optional.of(value) : Optional.empty();
            }

            T resolveAgainst(T originalValue) {
                switch (state) {
                case UNCHANGED:
                    return originalValue;
                case CLEAR:
                    return null;
                case SET:
                    return value;
                default:
                    throw new IllegalStateException("Unknown field update state: " + state);
                }
            }

            @Override
            public boolean equals(Object other) {
                if (other == this) {
                    return true;
                }
                if (!(other instanceof FieldUpdate<?>)) {
                    return false;
                }
                FieldUpdate<?> otherFieldUpdate = (FieldUpdate<?>) other;
                return state == otherFieldUpdate.state && Objects.equals(value, otherFieldUpdate.value);
            }

            @Override
            public int hashCode() {
                return Objects.hash(state, value);
            }
        }

        private Name name;
        private FieldUpdate<Phone> phoneUpdate = FieldUpdate.unchanged();
        private FieldUpdate<Facebook> facebookUpdate = FieldUpdate.unchanged();
        private FieldUpdate<Instagram> instagramUpdate = FieldUpdate.unchanged();
        private FieldUpdate<Address> addressUpdate = FieldUpdate.unchanged();
        private FieldUpdate<Remark> remarkUpdate = FieldUpdate.unchanged();
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            requireNonNull(toCopy);
            name = toCopy.name;
            phoneUpdate = toCopy.phoneUpdate;
            facebookUpdate = toCopy.facebookUpdate;
            instagramUpdate = toCopy.instagramUpdate;
            addressUpdate = toCopy.addressUpdate;
            remarkUpdate = toCopy.remarkUpdate;
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, tags)
                    || phoneUpdate.isEdited()
                    || facebookUpdate.isEdited()
                    || instagramUpdate.isEdited()
                    || addressUpdate.isEdited()
                    || remarkUpdate.isEdited();
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phoneUpdate = FieldUpdate.set(phone);
        }

        /** Marks phone as edited and clears it. */
        public void clearPhone() {
            this.phoneUpdate = FieldUpdate.clear();
        }

        public Optional<Phone> getPhone() {
            return phoneUpdate.getEditedValue();
        }

        public boolean isPhoneEdited() {
            return phoneUpdate.isEdited();
        }

        public void setFacebook(Facebook facebook) {
            this.facebookUpdate = FieldUpdate.set(facebook);
        }

        /** Marks Facebook as edited and clears it. */
        public void clearFacebook() {
            this.facebookUpdate = FieldUpdate.clear();
        }

        public Optional<Facebook> getFacebook() {
            return facebookUpdate.getEditedValue();
        }

        public boolean isFacebookEdited() {
            return facebookUpdate.isEdited();
        }

        public void setInstagram(Instagram instagram) {
            this.instagramUpdate = FieldUpdate.set(instagram);
        }

        /** Marks Instagram as edited and clears it. */
        public void clearInstagram() {
            this.instagramUpdate = FieldUpdate.clear();
        }

        public Optional<Instagram> getInstagram() {
            return instagramUpdate.getEditedValue();
        }

        public boolean isInstagramEdited() {
            return instagramUpdate.isEdited();
        }

        public void setAddress(Address address) {
            this.addressUpdate = FieldUpdate.set(address);
        }

        /** Marks address as edited and clears it. */
        public void clearAddress() {
            this.addressUpdate = FieldUpdate.clear();
        }

        public Optional<Address> getAddress() {
            return addressUpdate.getEditedValue();
        }

        public boolean isAddressEdited() {
            return addressUpdate.isEdited();
        }

        public void setRemark(Remark remark) {
            this.remarkUpdate = FieldUpdate.set(remark);
        }

        /** Marks remark as edited and clears it. */
        public void clearRemark() {
            this.remarkUpdate = FieldUpdate.clear();
        }

        public Optional<Remark> getRemark() {
            return remarkUpdate.getEditedValue();
        }

        public boolean isRemarkEdited() {
            return remarkUpdate.isEdited();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            assert tags == null || !tags.contains(null);
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phoneUpdate, otherEditPersonDescriptor.phoneUpdate)
                    && Objects.equals(facebookUpdate, otherEditPersonDescriptor.facebookUpdate)
                    && Objects.equals(instagramUpdate, otherEditPersonDescriptor.instagramUpdate)
                    && Objects.equals(addressUpdate, otherEditPersonDescriptor.addressUpdate)
                    && Objects.equals(remarkUpdate, otherEditPersonDescriptor.remarkUpdate)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", getPhone().orElse(null))
                    .add("isPhoneEdited", isPhoneEdited())
                    .add("facebook", getFacebook().orElse(null))
                    .add("isFacebookEdited", isFacebookEdited())
                    .add("instagram", getInstagram().orElse(null))
                    .add("isInstagramEdited", isInstagramEdited())
                    .add("address", getAddress().orElse(null))
                    .add("isAddressEdited", isAddressEdited())
                    .add("remark", getRemark().orElse(null))
                    .add("isRemarkEdited", isRemarkEdited())
                    .add("tags", tags)
                    .toString();
        }
    }
}
