package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        NAME, ADDRESS, PHONE, FACEBOOK, TAG, INSTAGRAM, REMARK
    }
    private final String searchPhrase;
    private final boolean isGeneralSearch;
    private final Map<SearchType, String> specificKeywords;

    /** Constructor for General Search */
    public PersonContainsKeywordsPredicate(String searchPhrase, boolean isGeneralSearch,
                                           Map<SearchType, String> specificKeywords) {

        requireNonNull(searchPhrase);
        requireNonNull(specificKeywords);

        this.searchPhrase = searchPhrase;
        this.isGeneralSearch = isGeneralSearch;
        this.specificKeywords = specificKeywords;
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person, "Person cannot be null");
        if (isGeneralSearch) {
            return testGeneral(person);
        }
        return testSpecific(person);
    }


    private boolean testGeneral(Person person) {
        assert person != null : "Person must be non-null for search";
        if (searchPhrase.isEmpty()) {
            return false;
        }
        String lowerPhrase = searchPhrase.toLowerCase();
        return person.getName().fullName.toLowerCase().contains(lowerPhrase)
                || person.getPhone().map(p -> p.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getFacebook().map(fb -> fb.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getInstagram().map(ig -> ig.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getAddress().map(a -> a.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getRemark().map(r -> r.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getTags().stream()
                .anyMatch(tag -> tag.tagName.toLowerCase().contains(lowerPhrase));
    }

    private boolean testSpecific(Person customer) {
        assert customer != null : "Person must be non-null for search";
        List<Predicate<Person>> predicateList = new ArrayList<>();

        if (specificKeywords.containsKey(SearchType.NAME)) {
            String val = specificKeywords.get(SearchType.NAME);
            predicateList.add(p -> p.getName().fullName.toLowerCase().contains(val.toLowerCase()));
        }

        if (specificKeywords.containsKey(SearchType.ADDRESS)) {
            String val = specificKeywords.get(SearchType.ADDRESS);
            predicateList.add(p -> p.getAddress().map(address -> address.value.contains(val)).orElse(false));
        }

        if (specificKeywords.containsKey(SearchType.PHONE)) {
            String val = specificKeywords.get(SearchType.PHONE);
            predicateList.add(p -> p.getPhone().map(phone -> phone.value.contains(val)).orElse(false));
        }

        if (specificKeywords.containsKey(SearchType.TAG)) {
            String val = specificKeywords.get(SearchType.TAG).toLowerCase();
            predicateList.add(person -> person.getTags().stream()
                    .anyMatch(tag -> tag.tagName.toLowerCase().contains(val)));
        }

        if (specificKeywords.containsKey(SearchType.FACEBOOK)) {
            String val = specificKeywords.get(SearchType.FACEBOOK);
            predicateList.add(p -> p.getFacebook().map(fb -> fb.value.contains(val)).orElse(false));
        }

        if (specificKeywords.containsKey(SearchType.INSTAGRAM)) {
            String val = specificKeywords.get(SearchType.INSTAGRAM);
            predicateList.add(p -> p.getInstagram().map(ig -> ig.value.contains(val)).orElse(false));
        }

        if (specificKeywords.containsKey(SearchType.REMARK)) {
            String val = specificKeywords.get(SearchType.REMARK);
            predicateList.add(p -> p.getRemark().map(remark -> remark.value.contains(val)).orElse(false));
        }

        if (predicateList.isEmpty()) {
            return false;
        }

        return predicateList.stream().allMatch(p -> p.test(customer));
    }

    public String getSummary() {
        if (isGeneralSearch) {
            return "Keyword: " + searchPhrase.trim();
        }

        return specificKeywords.entrySet().stream()
                .map(entry -> entry.getKey().toString() + ": " + entry.getValue())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .map(s -> "Keyword: [" + s + "]")
                .orElse("All");
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
        return isGeneralSearch == otherPredicate.isGeneralSearch
                && searchPhrase.equals(otherPredicate.searchPhrase)
                && specificKeywords.equals(otherPredicate.specificKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("searchPhrase", searchPhrase)
                .add("isGeneralSearch", isGeneralSearch)
                .add("specificKeywords", specificKeywords).toString();
    }
}
