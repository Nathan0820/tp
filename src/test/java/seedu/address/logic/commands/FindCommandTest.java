package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Map<PersonContainsKeywordsPredicate.SearchType, String> createMap(
            PersonContainsKeywordsPredicate.SearchType type, String value) {
        Map<PersonContainsKeywordsPredicate.SearchType, String> map = new HashMap<>();
        map.put(type, value);
        return map;
    }

    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate("first", true, new HashMap<>());
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate("second", true, new HashMap<>());

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void constructor_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        FindCommand command = new FindCommand(new PersonContainsKeywordsPredicate("x", true,
                new HashMap<>()));
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_noMatch_noPersonFound() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "xyznonexistent", true, new HashMap<>());
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0, predicate.getSummary());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_phraseMatch_multiplePersonsFound() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "street", true, new HashMap<>());
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3, predicate.getSummary());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "keyword", true, new HashMap<>());
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    @Test
    public void execute_specificNameMatch_personFound() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "n/Alice", false,
                createMap(PersonContainsKeywordsPredicate.SearchType.NAME, "Alice"));
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1, predicate.getSummary());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_specificPhoneMatch_personFound() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "p/9876", false,
                createMap(PersonContainsKeywordsPredicate.SearchType.PHONE, "9876"));
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1, predicate.getSummary());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_specificTagMatch_multiplePersonsFound() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "t/friends", false,
                createMap(PersonContainsKeywordsPredicate.SearchType.TAG, "friends"));
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3, predicate.getSummary());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_wrongSpecificField_noPersonFound() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "p/Alice", false,
                createMap(PersonContainsKeywordsPredicate.SearchType.PHONE, "Alice"));
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0, predicate.getSummary());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }
}
