package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    private Map<PersonContainsKeywordsPredicate.SearchType, String> createMap(
            PersonContainsKeywordsPredicate.SearchType type, String value) {
        Map<PersonContainsKeywordsPredicate.SearchType, String> map = new HashMap<>();
        map.put(type, value);
        return map;
    }

    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(
                "first", true, new HashMap<>());
        PersonContainsKeywordsPredicate secondPredicate = new PersonContainsKeywordsPredicate(
                "second", true, new HashMap<>());

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy = new PersonContainsKeywordsPredicate(
                "first", true, new HashMap<>());
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different search phrase -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // same phrase but general vs field-specific search -> not equal
        PersonContainsKeywordsPredicate generalAlice = new PersonContainsKeywordsPredicate("Alice",
                true, new HashMap<>());
        PersonContainsKeywordsPredicate nameAlice = new PersonContainsKeywordsPredicate("Alice", false,
                createMap(PersonContainsKeywordsPredicate.SearchType.NAME, "Alice"));
        assertFalse(generalAlice.equals(nameAlice));
    }

    @Test
    public void nullSearchPhrase_constructorThrows() {
        assertThrows(NullPointerException.class, () -> new PersonContainsKeywordsPredicate(
                null, true, new HashMap<>()));
    }

    @Test
    public void nullSearchMap_specificConstructorThrows() {
        assertThrows(NullPointerException.class, () -> new PersonContainsKeywordsPredicate("x", true, null));
    }

    @Test
    public void nullPerson_testThrows() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("any", true, new HashMap<>());
        assertThrows(NullPointerException.class, () -> predicate.test(null));
    }

    @Test
    public void test_nameContainsPhrase_returnsTrue() {
        // Full name match
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "Alex Yeoh", true, new HashMap<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));

        // Partial name match (substring)
        predicate = new PersonContainsKeywordsPredicate(
                "Alex", true, new HashMap<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));

        // Case-insensitive match
        predicate = new PersonContainsKeywordsPredicate(
                "aLex", true, new HashMap<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));
    }

    @Test
    public void test_phoneContainsPhrase_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "12345", true, new HashMap<>());
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_facebookContainsPhrase_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "alice.pau", true, new HashMap<>());
        assertTrue(predicate.test(new PersonBuilder().withFacebook("alice.pauline").build()));
    }

    @Test
    public void test_addressContainsPhrase_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "Main Street", true, new HashMap<>());
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));
    }

    @Test
    public void test_tagContainsPhrase_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "friend", true, new HashMap<>());
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_noFieldContainsPhrase_returnsFalse() {
        // Empty search phrase
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("", true, new HashMap<>());
        assertFalse(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));

        // Non-matching phrase
        predicate = new PersonContainsKeywordsPredicate("Zebra", true, new HashMap<>());
        assertFalse(predicate.test(new PersonBuilder().withName("Alex Yeoh").withPhone("12345")
                .withFacebook("alice.pauline").withAddress("Main Street").build()));
    }

    @Test
    public void test_specificSearch_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("n/Alice",
                false, createMap(PersonContainsKeywordsPredicate.SearchType.NAME, "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonContainsKeywordsPredicate("t/friends",
                false, createMap(PersonContainsKeywordsPredicate.SearchType.TAG, "friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        predicate = new PersonContainsKeywordsPredicate("a/Ang Mo Kio",
                false, createMap(
                        PersonContainsKeywordsPredicate.SearchType.ADDRESS, "Ang Mo Kio"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Ang Mo Kio").build()));

        predicate = new PersonContainsKeywordsPredicate("fb/randomFbAccount",
                false, createMap(
                        PersonContainsKeywordsPredicate.SearchType.FACEBOOK, "randomFbAccount"));
        assertTrue(predicate.test(new PersonBuilder().withFacebook("randomFbAccount").build()));

        predicate = new PersonContainsKeywordsPredicate("ig/randomIgAccount",
                false, createMap(
                PersonContainsKeywordsPredicate.SearchType.INSTAGRAM, "randomIgAccount"));
        assertTrue(predicate.test(new PersonBuilder().withInstagram("randomIgAccount").build()));

        predicate = new PersonContainsKeywordsPredicate("r/VIP",
                false, createMap(
                PersonContainsKeywordsPredicate.SearchType.REMARK, "VIP"));
        assertTrue(predicate.test(new PersonBuilder().withRemark("VIP").build()));
    }


    @Test
    public void test_specificSearch_returnsFalse() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("n/Alice",
                false, createMap(PersonContainsKeywordsPredicate.SearchType.NAME, "Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withPhone("91234567").build()));
    }

    @Test
    public void test_emptyMap_returnsFalse() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "", false, new HashMap<>());

        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testSpecificSearch_withMultiplePhrases_returnsTrue() {
        Map<PersonContainsKeywordsPredicate.SearchType, String> keywordsMap = new HashMap<>();
        keywordsMap.put(PersonContainsKeywordsPredicate.SearchType.NAME, "Alice");
        keywordsMap.put(PersonContainsKeywordsPredicate.SearchType.TAG, "friends");

        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "n/Alice t/friends", false, keywordsMap);

        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withTags("friends")
                .build()));
    }

    @Test
    public void testSpecificSearch_withMultiplePhrases_returnsFalse() {
        Map<PersonContainsKeywordsPredicate.SearchType, String> keywordsMap = new HashMap<>();
        keywordsMap.put(PersonContainsKeywordsPredicate.SearchType.NAME, "Alice");
        keywordsMap.put(PersonContainsKeywordsPredicate.SearchType.TAG, "friends");

        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("n/Alice t/friends",
                false, keywordsMap);
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("friends").build()));
    }

    @Test
    public void toStringMethod() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "testing phrase", true, new HashMap<>());

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName()
                + "{searchPhrase=testing phrase, isGeneralSearch=true, specificKeywords={}}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void toStringMethod_withSpecificKeywords() {
        Map<PersonContainsKeywordsPredicate.SearchType, String> keywords = new HashMap<>();
        keywords.put(PersonContainsKeywordsPredicate.SearchType.NAME, "Alice");

        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                "n/Alice", false, keywords);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName()
                + "{searchPhrase=n/Alice, isGeneralSearch=false, specificKeywords={NAME=Alice}}";

        assertEquals(expected, predicate.toString());
    }
}
