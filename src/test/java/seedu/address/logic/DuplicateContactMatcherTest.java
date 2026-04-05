package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DuplicateContactMatcherTest {

    @Test
    public void findWarning_noMatches_empty() {
        Person noMatch = new PersonBuilder()
                .withName("Independent Customer")
                .withPhone("99998888")
                .withFacebook("independent.fb")
                .withInstagram("independent_ig")
                .withAddress("1 Independent Street")
                .build();

        assertTrue(DuplicateContactMatcher.findWarning(noMatch, List.of(ALICE)).isEmpty());
    }

    @Test
    public void findWarning_singleMatch_returnsWarning() {
        Person samePhone = new PersonBuilder()
                .withName("Twin")
                .withPhone(ALICE.getPhone().orElseThrow().toString())
                .withoutFacebook()
                .withoutInstagram()
                .withoutAddress()
                .build();

        DuplicateContactMatcher.DuplicateContactWarning warning = DuplicateContactMatcher
                .findWarning(samePhone, List.of(ALICE))
                .orElseThrow();

        assertEquals(List.of("phone"), List.copyOf(warning.getMatchedFields()));
        assertEquals(1, warning.getTotalMatchCount());
        assertEquals(1, warning.getShownMatchCount());
        assertEquals("Alice Pauline", warning.getTopMatches().get(0).getCustomerName());
    }

    @Test
    public void findWarning_ranksByNumberOfMatches_thenName() {
        Person strongMatch = new PersonBuilder()
                .withName("Strong Match")
                .withPhone(ALICE.getPhone().orElseThrow().toString())
                .withFacebook(ALICE.getFacebook().orElseThrow().toString())
                .withInstagram(ALICE.getInstagram().orElseThrow().toString())
                .withoutAddress()
                .build(); // 2 matches

        Person mediumMatch = new PersonBuilder()
                .withName("Medium Match")
                .withPhone(ALICE.getPhone().orElseThrow().toString())
                .withoutFacebook()
                .withoutInstagram()
                .withoutAddress()
                .build(); // 1 match

        Person alphaMatch = new PersonBuilder()
                .withName("Alpha Match")
                .withPhone(ALICE.getPhone().orElseThrow().toString())
                .withoutFacebook()
                .withoutInstagram()
                .withoutAddress()
                .build(); // 1 match

        DuplicateContactMatcher.DuplicateContactWarning warning = DuplicateContactMatcher
                .findWarning(ALICE, List.of(alphaMatch, strongMatch, mediumMatch))
                .orElseThrow();

        // Rank by number of matches (Strong first)
        assertEquals("Strong Match", warning.getTopMatches().get(0).getCustomerName());

        // Rank alphabetically for same number of matches (Alpha before Medium)
        assertEquals("Alpha Match", warning.getTopMatches().get(1).getCustomerName());
        assertEquals("Medium Match", warning.getTopMatches().get(2).getCustomerName());

        assertEquals(3, warning.getTotalMatchCount());
        assertEquals(3, warning.getShownMatchCount());
    }

    @Test
    public void findWarning_truncatesToThreeMatches() {
        Person first = new PersonBuilder().withName("Alpha").withPhone(ALICE.getPhone().orElseThrow().toString())
                .withoutFacebook().withoutInstagram().withoutAddress().build();
        Person second = new PersonBuilder().withName("Beta").withPhone(ALICE.getPhone().orElseThrow().toString())
                .withoutFacebook().withoutInstagram().withoutAddress().build();
        Person third = new PersonBuilder().withName("Gamma").withPhone(ALICE.getPhone().orElseThrow().toString())
                .withoutFacebook().withoutInstagram().withoutAddress().build();
        Person fourth = new PersonBuilder().withName("Delta").withPhone(ALICE.getPhone().orElseThrow().toString())
                .withoutFacebook().withoutInstagram().withoutAddress().build();

        DuplicateContactMatcher.DuplicateContactWarning warning = DuplicateContactMatcher
                .findWarning(ALICE, List.of(first, second, third, fourth))
                .orElseThrow();

        assertEquals(4, warning.getTotalMatchCount());
        assertEquals(3, warning.getShownMatchCount());
        assertEquals(3, warning.getTopMatches().size());
        assertFalse(warning.getTopMatches().stream().anyMatch(match -> match.getCustomerName().equals("Gamma")));
    }
}

