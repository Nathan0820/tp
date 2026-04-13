package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s2-cs2103t-w09-3.github.io/tp/UserGuide.html";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private VBox helpContentBox;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        buildHelpContent();
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Builds the styled help content as structured command blocks.
     */
    private void buildHelpContent() {
        Label heading = new Label("Command Summary");
        heading.getStyleClass().add("help-heading");

        Separator headingSeparator = new Separator();
        headingSeparator.getStyleClass().add("help-separator");

        helpContentBox.getChildren().addAll(heading, headingSeparator);

        addCommandBlock("help", "Shows a message explaining how to access the help page.",
                "help",
                "help");

        addCommandBlock("add", "Adds a customer to the customer database.",
                "add n/NAME [p/PHONE] [ig/INSTAGRAM] [fb/FACEBOOK] [a/ADDRESS] [r/REMARK] [t/TAG]…​",
                "add n/John Doe p/98765432 a/John Street, Blk 123, #01-01 r/favorite customer");

        addCommandBlock("list", "Shows a list of all customers in the customer database.",
                "list",
                "list");

        addCommandBlock("edit", "Edits an existing customer in the displayed customer list.",
                "edit INDEX [n/NAME] [p/PHONE] [ig/INSTAGRAM] [fb/FACEBOOK] [a/ADDRESS] [r/REMARK] [t/TAG]…​",
                "edit 1 p/91234567 a/John Street, Blk 123, #02-02 r/favorite customer");

        addCommandBlock("find", "Finds customers whose details match the given keywords. "
                + "You can search across all fields or target a specific field using prefixes.",
                "find KEYWORD or find PREFIX/KEYWORD",
                "find John or find n/John");

        addCommandBlock("delete", "Deletes the specified customer from the customer database.",
                "delete INDEX",
                "delete 3");
        Separator orderSeparator = new Separator();
        orderSeparator.getStyleClass().add("help-separator");
        helpContentBox.getChildren().addAll(orderSeparator);

        addCommandBlock("order", "Adds a new order for a specific customer.",
                "order INDEX i/ITEM_NAME q/QUANTITY at/DATE [a/DELIVERY_ADDRESS] [s/STATUS]",
                "order 2 i/Burger q/5 at/2026-03-15 1800 a/123 Jurong West St 42, #05-01");

        addCommandBlock("delete-o", "Deletes the specified order from the order database.",
                "delete-o ORDER_INDEX",
                "delete-o 1");

        addCommandBlock("find-o", "Search for different orders with 4 category options: "
                + "item name, delivery address, customer index, status",
                "find-o Category-Type/Category-Keywords",
                "find-o i/pizza");

        addCommandBlock("edit-o", "Updates fields of an existing order. "
                + "Any field you specify replaces the previous value; other fields stay unchanged. ",
                "edit-o ORDER_INDEX [i/ITEM_NAME] [q/QUANTITY] [at/DATE] [a/DELIVERY_ADDRESS] [s/STATUS]",
                "edit-o 1 i/Pizza q/3 at/2026-04-02 1200");

        addCommandBlock("list-o", "Shows a list of all orders in the order database.",
                "list-o",
                "list-o");

        addCommandBlock("clear",
                "Shows the confirmation warning for clearing all entries. "
                        + "'clear CONFIRM' directly and permanently deletes all customers and orders; "
                        + "a prior 'clear' is optional.",
                "clear [CONFIRM]",
                "clear CONFIRM");

        addCommandBlock("exit", "Exits the program.",
                "exit",
                "exit");

        Separator footerSeparator = new Separator();
        footerSeparator.getStyleClass().add("help-separator");

        Label footer = new Label("For full details, click the button below to copy the URL to the user guide!");
        footer.getStyleClass().add("help-footer");
        footer.setWrapText(true);

        helpContentBox.getChildren().addAll(footerSeparator, footer);
    }

    /**
     * Adds a command block to the help content box.
     * @param command The command to add.
     * @param description The description of the command.
     * @param format The format of the command.
     * @param example The example of the command.
     */
    private void addCommandBlock(String command, String description, String format, String example) {
        VBox block = new VBox(4);
        block.getStyleClass().add("help-block");

        HBox commandRow = new HBox(8);
        commandRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        Label cmdLabel = new Label(command);
        cmdLabel.getStyleClass().add("help-command");
        cmdLabel.setMinWidth(Label.USE_PREF_SIZE);
        Label descLabel = new Label(description);
        descLabel.getStyleClass().add("help-description");
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(descLabel, Priority.ALWAYS);
        commandRow.getChildren().addAll(cmdLabel, descLabel);

        Label formatLabel = new Label("Format:  " + format);
        formatLabel.getStyleClass().add("help-format");
        formatLabel.setWrapText(true);
        VBox.setMargin(formatLabel, new Insets(0, 0, 0, 10));

        Label exampleLabel = new Label("Example: " + example);
        exampleLabel.getStyleClass().add("help-example");
        exampleLabel.setWrapText(true);
        VBox.setMargin(exampleLabel, new Insets(0, 0, 0, 10));

        block.getChildren().addAll(commandRow, formatLabel, exampleLabel);
        helpContentBox.getChildren().add(block);
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
