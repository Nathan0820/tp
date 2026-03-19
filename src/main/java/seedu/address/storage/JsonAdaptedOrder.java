package seedu.address.storage;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Order;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Order}.
 */
public class JsonAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    private final String item;
    private final String quantity;
    private final String deliveryTime;
    private final String address;
    private final String status;
    private final String customerId;

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(
            @JsonProperty("item") String item,
            @JsonProperty("quantity") String quantity,
            @JsonProperty("deliveryTime") String deliveryTime,
            @JsonProperty("address") String address,
            @JsonProperty("status") String status,
            @JsonProperty("customerId") String customerId) {

        this.item = item;
        this.quantity = quantity;
        this.deliveryTime = deliveryTime;
        this.address = address;
        this.status = status;
        this.customerId = customerId;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order source) {
        this.item = source.getItem().value;
        this.quantity = source.getQuantity().value;
        this.deliveryTime = source.getDeliveryTime().value;
        this.address = source.getAddress().value;
        this.status = source.getStatus().value;
        this.customerId = source.getCustomerId().toString();
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @param persons the list of persons loaded from storage, used to resolve customer index.
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Order toModelType(List<Person> persons) throws IllegalValueException {

        // ITEM
        if (item == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Item.class.getSimpleName()));
        }
        final Item modelItem;
        try {
            modelItem = new Item(item);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }

        // QUANTITY
        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }
        final Quantity modelQuantity;
        try {
            modelQuantity = new Quantity(quantity);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }

        // DELIVERY TIME
        if (deliveryTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DeliveryTime.class.getSimpleName()));
        }
        final DeliveryTime modelDeliveryTime;
        try {
            modelDeliveryTime = new DeliveryTime(deliveryTime);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }

        // ADDRESS
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        final Address modelAddress;
        try {
            modelAddress = new Address(address);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }

        // STATUS
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        final Status modelStatus;
        try {
            modelStatus = new Status(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }

        // CUSTOMER ID
        if (customerId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Customer Id"));
        }

        final UUID modelCustomerId;
        try {
            modelCustomerId = UUID.fromString(customerId);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Customer Id must be a valid UUID.");
        }

        return new Order(modelCustomerId, modelItem, modelQuantity, modelDeliveryTime, modelAddress, modelStatus);
    }
}
