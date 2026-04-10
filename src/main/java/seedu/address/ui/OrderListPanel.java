package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of orders.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private Person currentContextPerson;
    private ObservableList<Person> personList;

    @FXML
    private Label ordersContextLabel;

    @FXML
    private ListView<Order> orderListView;

    /**
     * Creates an {@code OrderListPanel} with the given {@code ObservableList} of orders and persons.
     * Orders are displayed in ascending numerical order.
     */
    public OrderListPanel(ObservableList<Order> orderList, ObservableList<Person> personList) {
        super(FXML);
        this.personList = personList;

        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());

        orderListView.getItems().addListener((ListChangeListener<Order>) change -> updateOrdersHeader());
        updateOrdersHeader();
    }

    /**
     * Updates the sub-heading to match the customer whose orders are in view.
     */
    public void setOrdersContext(Person person) {
        currentContextPerson = person;
        updateOrdersHeader();
    }

    /**
     * Updates the header of the orders list to match the current context person.
     */
    private void updateOrdersHeader() {
        boolean isEmpty = orderListView.getItems() == null || orderListView.getItems().isEmpty();

        if (isEmpty) {
            if (currentContextPerson == null) {
                ordersContextLabel.setText("No orders to display. Add one using 'order' command.");
            } else {
                ordersContextLabel.setText("No orders for " + currentContextPerson.getName().fullName
                    + ". Add one using 'order' command.");
            }
            return;
        }

        if (currentContextPerson == null) {
            ordersContextLabel.setText("Orders");
        } else {
            ordersContextLabel.setText("Orders for " + currentContextPerson.getName().fullName);
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Order} using an {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<Order> {

        OrderListViewCell() {
        }

        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);

            if (getGraphic() instanceof Region oldRoot) {
                oldRoot.maxWidthProperty().unbind();
            }

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                // Look up the customer person by customerId
                Person customerPerson = personList.stream()
                        .filter(person -> person.getId().equals(order.getCustomerId()))
                        .findFirst()
                        .orElse(null);

                OrderCard card = new OrderCard(order, getIndex() + 1, customerPerson);
                Region cardRoot = card.getRoot();
                cardRoot.maxWidthProperty().bind(orderListView.widthProperty().subtract(32));
                setGraphic(cardRoot);
            }
        }
    }

}
