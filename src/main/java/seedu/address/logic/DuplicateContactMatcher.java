package seedu.address.logic;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Person;

/**
 * Finds customers that share exact contact details with a given customer.
 */
public final class DuplicateContactMatcher {

    private DuplicateContactMatcher() {}

    /**
     * Returns matched contact field names if the subject shares contact details with any existing customer.
     */
    public static Optional<Set<String>> findWarning(Person subject, List<Person> existingPersons) {
        assert subject != null;
        assert existingPersons != null;

        Set<String> matchedFields = new LinkedHashSet<>();
        for (Person existingPerson : existingPersons) {
            matchedFields.addAll(subject.getMatchingContactFields(existingPerson));
        }
        return matchedFields.isEmpty() ? Optional.empty() : Optional.of(Set.copyOf(matchedFields));
    }
}
