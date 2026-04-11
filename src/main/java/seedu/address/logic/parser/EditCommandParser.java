package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final Logger logger = LogsCenter.getLogger(EditCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.info("Parsing EditCommand with args: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_FACEBOOK, PREFIX_INSTAGRAM, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_TAG);

        Index index;
        String preamble = argMultimap.getPreamble();

        try {
            index = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            logger.warning("Invalid index format: " + preamble);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_FACEBOOK, PREFIX_INSTAGRAM, PREFIX_ADDRESS, PREFIX_REMARK);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String nameValue = argMultimap.getValue(PREFIX_NAME).get();
            editPersonDescriptor.setName(ParserUtil.parseName(ParserUtil.ensureNoUnsupportedPrefixTokensInValue(
                    nameValue, ParserUtil.PREFIXES_FOR_PERSON_COMMAND)));
        }

        Optional<String> phoneValue = argMultimap.getValue(PREFIX_PHONE);
        if (phoneValue.isPresent()) {
            if (phoneValue.get().isEmpty()) { // phone argument is an empty string
                editPersonDescriptor.clearPhone();
            } else {
                editPersonDescriptor.setPhone(ParserUtil.parsePhone(ParserUtil.ensureNoUnsupportedPrefixTokensInValue(
                        phoneValue.get(), ParserUtil.PREFIXES_FOR_PERSON_COMMAND)));
            }
        }

        Optional<String> facebookValue = argMultimap.getValue(PREFIX_FACEBOOK);
        if (facebookValue.isPresent()) {
            if (facebookValue.get().isEmpty()) { // facebook argument is an empty string
                editPersonDescriptor.clearFacebook();
            } else {
                editPersonDescriptor.setFacebook(ParserUtil.parseFacebook(
                        ParserUtil.ensureNoUnsupportedPrefixTokensInValue(
                                facebookValue.get(), ParserUtil.PREFIXES_FOR_PERSON_COMMAND)));
            }
        }

        Optional<String> instagramValue = argMultimap.getValue(PREFIX_INSTAGRAM);
        if (instagramValue.isPresent()) {
            if (instagramValue.get().isEmpty()) {
                editPersonDescriptor.clearInstagram();
            } else {
                editPersonDescriptor.setInstagram(ParserUtil.parseInstagram(
                        ParserUtil.ensureNoUnsupportedPrefixTokensInValue(
                                instagramValue.get(), ParserUtil.PREFIXES_FOR_PERSON_COMMAND)));
            }
        }

        Optional<String> addressValue = argMultimap.getValue(PREFIX_ADDRESS);
        if (addressValue.isPresent()) {
            if (addressValue.get().isEmpty()) {
                editPersonDescriptor.clearAddress();
            } else {
                editPersonDescriptor.setAddress(ParserUtil.parseAddress(addressValue.get()));
            }
        }

        Optional<String> remarkValue = argMultimap.getValue(PREFIX_REMARK);
        if (remarkValue.isPresent()) {
            if (remarkValue.get().isEmpty()) {
                editPersonDescriptor.clearRemark();
            } else {
                editPersonDescriptor.setRemark(ParserUtil.parseRemark(remarkValue.get()));
            }
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            logger.warning("Edit command rejected: no fields edited.");
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        logger.info("Successfully parsed EditCommand for index: " + index.getOneBased());

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }

        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
