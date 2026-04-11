package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.Person.MATCH_FIELD_FACEBOOK;
import static seedu.address.model.person.Person.MATCH_FIELD_INSTAGRAM;
import static seedu.address.model.person.Person.MATCH_FIELD_PHONE;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DuplicateContactMatcherTest {

    // ========== Null Handling ==========
    @Test
    public void findWarning_nullSubject_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> DuplicateContactMatcher.findWarning(null, List.of(ALICE)));
    }

    @Test
    public void findWarning_nullExistingPersonsList_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> DuplicateContactMatcher.findWarning(ALICE, null));
    }

    @Test
    public void findWarning_emptyExistingPersonsList_returnsEmpty() {
        assertTrue(DuplicateContactMatcher.findWarning(ALICE, Collections.emptyList()).isEmpty());
    }

    @Test
    public void findWarning_noMatches_empty() {
        Person noMatch = new PersonBuilder()
                .withName("Dummy Customer")
                .withPhone("99998888")
                .withFacebook("dummy.fb")
                .withInstagram("dummy.ig")
                .withoutAddress()
                .build();

        assertTrue(DuplicateContactMatcher.findWarning(noMatch, List.of(ALICE)).isEmpty());
    }

    @Test
    public void findWarning_noContactMethodsInSubject_empty() {
        Person noContacts = new PersonBuilder()
                .withName("No Contact")
                .withoutPhone()
                .withoutFacebook()
                .withoutInstagram()
                .build();

        assertTrue(DuplicateContactMatcher.findWarning(noContacts, List.of(ALICE)).isEmpty());
    }

    @Test
    public void findWarning_noContactMethodsInExistingPersons_empty() {
        Person noContacts = new PersonBuilder()
                .withName("No Contact")
                .withoutPhone()
                .withoutFacebook()
                .withoutInstagram()
                .build();

        assertTrue(DuplicateContactMatcher.findWarning(ALICE, List.of(noContacts)).isEmpty());
    }

    // ========== Single Match in Single Existing Person ==========
    @Test
    public void findWarning_phoneMatch_returnsPhoneField() {
        Person samePhone = new PersonBuilder()
                .withName("Twin Phone")
                .withPhone(ALICE.getPhone().orElseThrow().toString())
                .withoutFacebook()
                .withoutInstagram()
                .withoutAddress()
                .build();

        Set<String> warningFields = DuplicateContactMatcher.findWarning(samePhone, List.of(ALICE)).orElseThrow();
        assertEquals(Set.of(MATCH_FIELD_PHONE), warningFields);
    }

    @Test
    public void findWarning_facebookMatch_returnsFacebookField() {
        Person sameFacebook = new PersonBuilder()
                .withName("Twin Facebook")
                .withoutPhone()
                .withFacebook(ALICE.getFacebook().orElseThrow().toString())
                .withoutInstagram()
                .withoutAddress()
                .build();

        Set<String> warningFields = DuplicateContactMatcher.findWarning(sameFacebook, List.of(ALICE)).orElseThrow();
        assertEquals(Set.of(MATCH_FIELD_FACEBOOK), warningFields);
    }

    @Test
    public void findWarning_instagramMatch_returnsInstagramField() {
        Person sameInstagram = new PersonBuilder()
                .withName("Twin Instagram")
                .withoutPhone()
                .withoutFacebook()
                .withInstagram(ALICE.getInstagram().orElseThrow().toString())
                .withoutAddress()
                .build();

        Set<String> warningFields = DuplicateContactMatcher.findWarning(sameInstagram, List.of(ALICE)).orElseThrow();
        assertEquals(Set.of(MATCH_FIELD_INSTAGRAM), warningFields);
    }

    // ========== Multiple Matches in Single Existing Person ==========
    @Test
    public void findWarning_twoFieldsMatchInSinglePerson_returnsUnion() {
        Person sameTwoFields = new PersonBuilder()
                .withName("Twin Two Fields")
                .withPhone(ALICE.getPhone().orElseThrow().toString())
                .withFacebook(ALICE.getFacebook().orElseThrow().toString())
                .withoutInstagram()
                .withoutAddress()
                .build();

        Set<String> warningFields = DuplicateContactMatcher.findWarning(sameTwoFields, List.of(ALICE)).orElseThrow();
        assertEquals(Set.of(MATCH_FIELD_PHONE, MATCH_FIELD_FACEBOOK), warningFields);
    }

    @Test
    public void findWarning_allThreeFieldsMatchInSinglePerson_returnsUnion() {
        Person sameAllFields = new PersonBuilder()
                .withName("Complete Twin")
                .withPhone(ALICE.getPhone().orElseThrow().toString())
                .withFacebook(ALICE.getFacebook().orElseThrow().toString())
                .withInstagram(ALICE.getInstagram().orElseThrow().toString())
                .withoutAddress()
                .build();

        Set<String> warningFields = DuplicateContactMatcher.findWarning(sameAllFields, List.of(ALICE)).orElseThrow();
        assertEquals(Set.of(MATCH_FIELD_PHONE, MATCH_FIELD_FACEBOOK, MATCH_FIELD_INSTAGRAM), warningFields);
    }

    // ========== Multiple Matches Across Multiple Existing Persons ==========
    @Test
    public void findWarning_nonOverlappingFieldMatches_returnsUnionOfFields() {
        Person sameFacebook = new PersonBuilder()
                .withName("Same Facebook")
                .withFacebook(ALICE.getFacebook().orElseThrow().toString())
                .withoutPhone()
                .withoutInstagram()
                .withoutAddress()
                .build();

        Person sameInstagram = new PersonBuilder()
                .withName("Same Instagram")
                .withInstagram(ALICE.getInstagram().orElseThrow().toString())
                .withoutPhone()
                .withoutFacebook()
                .withoutAddress()
                .build();

        Set<String> warningFields = DuplicateContactMatcher.findWarning(ALICE, List.of(sameFacebook, sameInstagram))
                .orElseThrow();
        assertEquals(Set.of(MATCH_FIELD_FACEBOOK, MATCH_FIELD_INSTAGRAM), warningFields);
    }

    @Test
    public void findWarning_overlappingFieldMatches_returnsUniqueFieldSet() {
        Person sameBoth = new PersonBuilder()
                .withName("Same Both")
                .withPhone(ALICE.getPhone().orElseThrow().toString())
                .withFacebook(ALICE.getFacebook().orElseThrow().toString())
                .withoutInstagram()
                .withoutAddress()
                .build();

        Person samePhone = new PersonBuilder()
                .withName("Same Phone Only")
                .withPhone(ALICE.getPhone().orElseThrow().toString())
                .withoutFacebook()
                .withoutInstagram()
                .withoutAddress()
                .build();

        Set<String> warningFields = DuplicateContactMatcher.findWarning(ALICE, List.of(sameBoth, samePhone))
                .orElseThrow();

        // Should deduplicate and return only unique fields
        assertEquals(Set.of(MATCH_FIELD_PHONE, MATCH_FIELD_FACEBOOK), warningFields);
    }

    // ========== Self-Comparison (Matching Against Oneself) ==========
    @Test
    public void findWarning_subjectMatchesItself_returnsAllFields() {
        // If ALICE is in the list and we're matching ALICE against it
        Set<String> warningFields = DuplicateContactMatcher.findWarning(ALICE, List.of(ALICE))
                .orElseThrow();
        assertEquals(Set.of(MATCH_FIELD_PHONE, MATCH_FIELD_FACEBOOK, MATCH_FIELD_INSTAGRAM), warningFields);
    }
}
