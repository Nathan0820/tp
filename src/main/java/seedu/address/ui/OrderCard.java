package seedu.address.ui;

import java.util.UUID;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of an {@code Order}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    public final Order order;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label customerName;
    @FXML
    private Label item;
    @FXML
    private Label status;
    @FXML
    private Label date;

    /**
     * Creates an {@code OrderCard} with the given {@code Order} and index to display.
     */
    public OrderCard(Order order, int displayedIndex, ObservableList<Person> personList) {
        super(FXML);
        this.order = order;

        id.setText("#" + displayedIndex);

        String customerNameText = getCustomerName(order, personList);
        customerName.setText(customerNameText);

        item.setText(order.getItem().value + " x" + order.getQuantity().value);

        status.setText(order.getStatus().value);

        date.setText(order.getDeliveryTime().value);
    }

    /**
     * Returns the customer name for the given order by looking up the customer index.
     */
    static String getCustomerName(Order order, ObservableList<Person> personList) {
        UUID customerId = order.getCustomerId();

        for (Person p : personList) {
            if (p.getId().equals(customerId)) {
                return p.getName().fullName;
            }
        }

        return "Unknown Customer";
    }
}
