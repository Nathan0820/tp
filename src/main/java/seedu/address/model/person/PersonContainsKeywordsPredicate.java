package seedu.address.model.person;


import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s fields contain the given search phrase as a case-insensitive substring.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    /**
     * Specifies the type of search to perform.
     */
    public enum SearchType {
        NAME, ADDRESS, PHONE, EMAIL, TAG
    }
    private final String searchPhrase;
    private final boolean isGeneralSearch;
    private final SearchType searchType;

    /** Constructor for General Search */
    public PersonContainsKeywordsPredicate(String searchPhrase) {
        this.searchPhrase = searchPhrase;
        this.isGeneralSearch = true;
        this.searchType = null;
    }

    /** Constructor for Specific Search */
    public PersonContainsKeywordsPredicate(String searchPhrase, SearchType searchType) {
        this.searchPhrase = searchPhrase;
        this.isGeneralSearch = false;
        this.searchType = searchType;
    }

    @Override
    public boolean test(Person person) {
        if (isGeneralSearch) {
            return testGeneral(person);
        }
        return testSpecific(person);
    }


    private boolean testGeneral(Person person) {
        if (searchPhrase.isEmpty()) {
            return false;
        }
        String lowerPhrase = searchPhrase.toLowerCase();
        return person.getName().fullName.toLowerCase().contains(lowerPhrase)
                || person.getPhone().map(p -> p.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getEmail().map(e -> e.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getAddress().map(a -> a.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getTags().stream()
                .anyMatch(tag -> tag.tagName.toLowerCase().contains(lowerPhrase));
    }

    private boolean testSpecific(Person person) {
        switch (searchType) {
        case NAME:
            return person.getName().toString().toLowerCase().contains(searchPhrase.toLowerCase());
        case ADDRESS:
            return person.getAddress().toString().toLowerCase().contains(searchPhrase.toLowerCase());
        case PHONE:
            return person.getPhone().toString().contains(searchPhrase.toLowerCase());
        case EMAIL:
            return person.getEmail().toString().toLowerCase().contains(searchPhrase.toLowerCase());
        case TAG:
            return person.getTags().stream()
                    .anyMatch(tag -> tag.toString().toLowerCase().contains(searchPhrase.toLowerCase()));
        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return searchPhrase.equals(otherPredicate.searchPhrase);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("searchPhrase", searchPhrase).toString();
    }
}
