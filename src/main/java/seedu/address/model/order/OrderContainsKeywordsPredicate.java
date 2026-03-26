package seedu.address.model.order;

import java.util.Map;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Order}'s fields contain the given search phrase.
 */
public class OrderContainsKeywordsPredicate implements Predicate<Order> {

    /**
     * Specifies the type of search to perform.
     */
    public enum SearchType {
        ITEM, //-i
        ADDRESS, //-a
        CUSTOMER, //-c
        STATUS; //-s
    }

    private final Map<SearchType, String> searchMap;

    /**
     * Constructs a predicate using a map of search types and their corresponding keywords.
     *
     * @param searchMap A map containing SearchType keys and their string values.
     */
    public OrderContainsKeywordsPredicate(Map<SearchType, String> searchMap) {
        this.searchMap = searchMap;
    }

    /**
     * Tests that a {@code Order}'s {@code Item}, {@code Address} or {@code Customer} matches the keyword given.
     */
    @Override
    public boolean test(Order order) {
        return searchMap.entrySet().stream().allMatch(entry -> {
            SearchType type = entry.getKey();
            String keyword = entry.getValue().toLowerCase();
            switch (type) {
            case ITEM:
                return order.getItem().toString().toLowerCase().contains(keyword.toLowerCase());
            case ADDRESS:
                return order.getAddress().toString().toLowerCase().contains(keyword.toLowerCase());
            case CUSTOMER:
                return order.getCustomerId().toString().equals(keyword);
            case STATUS:
                return order.getStatus().toString().toLowerCase().contains(keyword.toLowerCase());
            default:
                return false;
            }
        });
    }

    public Map<SearchType, String> getSearchMap() {
        return searchMap;
    }

    /**
     * Returns true if both predicates have the same search type and keyword.
     */
    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof OrderContainsKeywordsPredicate
                && searchMap.equals(((OrderContainsKeywordsPredicate) other).searchMap));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("searchMap", searchMap)
                .toString();
    }
}
