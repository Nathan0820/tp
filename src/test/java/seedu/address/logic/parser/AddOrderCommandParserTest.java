package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_2030;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_DESC_PIZZA;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_3;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_PREPARING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Order;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddOrderCommandParserTest {
    private AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index index = Index.fromOneBased(1);

        // Build a person so we can get a valid UUID
        Person customer = new PersonBuilder().withName("Alice").build();
        UUID customerId = customer.getId();

        Order expectedOrder = new OrderBuilder()
                .withCustomerId(customerId)
                .withItem("Pizza")
                .withQuantity("3")
                .withDeliveryTime("2030-01-01 1800")
                .withAddress("Block 312, Amy Street 1")
                .withStatus("PREPARING")
                .build();

        assertParseSuccess(parser,
                "1" + ITEM_DESC_PIZZA + QUANTITY_DESC_3 + DATETIME_DESC_2030
                        + ADDRESS_DESC_AMY + STATUS_DESC_PREPARING,
                new AddOrderCommand(
                        index,
                        expectedOrder.getItem(),
                        expectedOrder.getQuantity(),
                        expectedOrder.getDeliveryTime(),
                        Optional.of(expectedOrder.getAddress()),
                        Optional.of(expectedOrder.getStatus())
                ));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Index index = Index.fromOneBased(1);

        // Build a person so we can get a valid UUID
        Person customer = new PersonBuilder().withName("Alice").build();
        UUID customerId = customer.getId();

        // Missing address and status
        Order expectedOrder = new OrderBuilder()
                .withCustomerId(customerId)
                .withItem("Pizza")
                .withQuantity("3")
                .withDeliveryTime("2030-01-01 1800")
                .build();

        assertParseSuccess(parser,
                "1" + ITEM_DESC_PIZZA + QUANTITY_DESC_3 + DATETIME_DESC_2030,
                new AddOrderCommand(
                        index,
                        expectedOrder.getItem(),
                        expectedOrder.getQuantity(),
                        expectedOrder.getDeliveryTime(),
                        Optional.empty(),
                        Optional.empty()
                ));
    }

    @Test
    public void parse_missingCompulsoryFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddOrderCommand.MESSAGE_USAGE);

        // Missing index
        assertParseFailure(parser,
                ITEM_DESC_PIZZA + QUANTITY_DESC_3 + DATETIME_DESC_2030,
                expectedMessage);

        // Missing item
        assertParseFailure(parser,
                "1" + QUANTITY_DESC_3 + DATETIME_DESC_2030,
                expectedMessage);

        // Missing quantity
        assertParseFailure(parser,
                "1" + ITEM_DESC_PIZZA + DATETIME_DESC_2030,
                expectedMessage);

        // Missing datetime
        assertParseFailure(parser,
                "1" + ITEM_DESC_PIZZA + QUANTITY_DESC_3,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid index
        assertParseFailure(parser,
                "a" + ITEM_DESC_PIZZA + QUANTITY_DESC_3 + DATETIME_DESC_2030,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        // Invalid quantity
        assertParseFailure(parser,
                "1" + ITEM_DESC_PIZZA + INVALID_QUANTITY_DESC + DATETIME_DESC_2030,
                Quantity.MESSAGE_CONSTRAINTS);

        // Invalid datetime
        assertParseFailure(parser,
                "1" + ITEM_DESC_PIZZA + QUANTITY_DESC_3 + INVALID_DATETIME_DESC,
                DeliveryTime.MESSAGE_CONSTRAINTS);

        // Invalid address
        assertParseFailure(parser,
                "1" + ITEM_DESC_PIZZA + QUANTITY_DESC_3 + DATETIME_DESC_2030 + INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS);

        // Invalid status
        assertParseFailure(parser,
                "1" + ITEM_DESC_PIZZA + QUANTITY_DESC_3 + DATETIME_DESC_2030 + INVALID_STATUS_DESC,
                Status.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        assertParseFailure(parser,
                "1" + ITEM_DESC_PIZZA + ITEM_DESC_PIZZA + QUANTITY_DESC_3 + DATETIME_DESC_2030,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM));

        assertParseFailure(parser,
                "1" + ITEM_DESC_PIZZA + QUANTITY_DESC_3 + QUANTITY_DESC_3 + DATETIME_DESC_2030,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));

        assertParseFailure(parser,
                "1" + ITEM_DESC_PIZZA + QUANTITY_DESC_3 + DATETIME_DESC_2030 + DATETIME_DESC_2030,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATETIME));
    }
}
