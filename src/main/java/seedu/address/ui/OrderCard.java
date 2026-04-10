package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of an {@code Order}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    public final Order order;
    public final int displayedIndex;
    public final Person customer;
    @FXML
    private Label id;
    @FXML
    private Label item;
    @FXML
    private Label customerLabel;
    @FXML
    private Label quantity;
    @FXML
    private Label address;
    @FXML
    private Label date;
    @FXML
    private Label status;

    /**
     * Creates an {@code OrderCard} with the given {@code Order} and customer.
     */
    public OrderCard(Order order, int displayedIndex, Person customer) {
        super(FXML);
        this.order = order;
        this.displayedIndex = displayedIndex;
        this.customer = customer;

        applyStatusStyle(order.getStatus().value);

        id.setText("Order #" + displayedIndex);
        item.setText(order.getItem().value);
        quantity.setText("Quantity: " + order.getQuantity().value);

        String customerText = (customer != null) ? "Customer: " + customer.getName().fullName : "Customer: Unknown";
        customerLabel.setText(customerText);

        address.setText("Address: " + order.getAddress().value);

        date.setText("Date: " + order.getDeliveryTime().value);
        configureWrappingLabel(item);
        configureWrappingLabel(quantity);
        configureWrappingLabel(address);

        status.setText("Status: " + order.getStatus().value);
    }

    private void applyStatusStyle(String statusValue) {
        status.getStyleClass().removeIf(s -> s.startsWith("status-"));
        switch (statusValue) {
        case "PREPARING":
            status.getStyleClass().add("status-preparing");
            break;
        case "READY":
            status.getStyleClass().add("status-ready");
            break;
        case "DELIVERED":
            status.getStyleClass().add("status-delivered");
            break;
        case "CANCELLED":
            status.getStyleClass().add("status-cancelled");
            break;
        default:
            status.getStyleClass().add("status-unknown");
        }
    }

    private void configureWrappingLabel(Label label) {
        label.setWrapText(true);
        label.setMaxWidth(Double.MAX_VALUE);
    }
}
