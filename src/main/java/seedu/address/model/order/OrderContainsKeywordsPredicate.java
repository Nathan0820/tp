package seedu.address.model.order;

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
        CUSTOMER; //-c
    }

    private final SearchType searchType;
    private final String keyword;

    /**
     * Constructs a predicate with the given search type and keyword.
     *
     * @param searchType The type of search (ITEM, ADDRESS, or CUSTOMER)
     * @param keyword The search keyword
     */
    public OrderContainsKeywordsPredicate(SearchType searchType, String keyword) {
        this.searchType = searchType;
        this.keyword = keyword;
    }

    /**
     * Tests that a {@code Order}'s {@code Item}, {@code Address} or {@code Customer} matches the keyword given.
     */
    @Override
    public boolean test(Order order) {
        switch (searchType) {
        case ITEM:
            return order.getItem().toString().toLowerCase().contains(keyword.toLowerCase());
        case ADDRESS:
            return order.getAddress().toString().toLowerCase().contains(keyword.toLowerCase());
        case CUSTOMER:
            return order.getCustomerIndex().getOneBased() == Integer.parseInt(keyword);
        default:
            return false;
        }
    }

    /**
     * Returns true if both predicates have the same search type and keyword.
     */
    @Override
    public boolean equals(Object other) {
        OrderContainsKeywordsPredicate otherPredicate = (OrderContainsKeywordsPredicate) other;
        return other == this // short circuit if same object
                || (other instanceof OrderContainsKeywordsPredicate // instanceof handles nulls
                && searchType.equals(otherPredicate.searchType) // state check
                && keyword.equals(otherPredicate.keyword)); // state check
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keyword", keyword)
                .add("searchType", searchType)
                .toString();
    }
}
