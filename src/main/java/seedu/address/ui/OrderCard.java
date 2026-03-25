package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;

/**
 * An UI component that displays information of an {@code Order}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    public final Order order;

    @FXML
    private HBox cardPane;
    @FXML
    private Label item;
    @FXML
    private Label address;
    @FXML
    private Label date;
    @FXML
    private Label status;

    /**
     * Creates an {@code OrderCard} with the given {@code Order} and index to display.
     */
    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;

        switch (order.getStatus().value) {
        case "PREPARING":
            status.setStyle("-fx-background-color: #dce5f6; -fx-text-fill: #083fa7;"); // Lemon chiffon
            break;
        case "READY":
            status.setStyle("-fx-background-color: #f0f4c5; -fx-text-fill: #949e06;"); // Light green
            break;
        case "DELIVERED":
            status.setStyle("-fx-background-color: #c9f8c9; -fx-text-fill: #0bcb0b;"); // Pale green
            break;
        case "CANCELLED":
            status.setStyle("-fx-background-color: #f9cfd5; -fx-text-fill: #c20925;"); // Light pink
            break;
        default:
            status.setStyle("-fx-background-color: #cfcece; -fx-text-fill: black;");
        }

        item.setText("Order: " + order.getItem().value + " (x" + order.getQuantity().value + ")");

        address.setText("Address: " + order.getAddress().value);

        date.setText("Date: " + order.getDeliveryTime().value);

        status.setText("Status: " + order.getStatus().value);
    }
}
