package seedu.address.ui;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final double WRAP_LABEL_PADDING = 40;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label facebook;
    @FXML
    private Label instagram;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
      
        configureWrappingLabel(name);
        configureWrappingLabel(address);
        configureWrappingLabel(remark);
      
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        setOptionalLabel(phone, person.getPhone().map(p -> p.value), p -> p);
        setOptionalLabel(facebook, person.getFacebook().map(Facebook::getDisplayValue), fb -> "FB: " + fb);
        setOptionalLabel(instagram, person.getInstagram().map(Instagram::getDisplayValue), ig -> "IG: " + ig);
        setOptionalLabel(address, person.getAddress().map(a -> a.value), a -> a);
        setOptionalLabel(remark, person.getRemark().map(r -> r.value), r -> "📝 " + r);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(createTagLabel(tag.tagName)));
    }

    /**
     * Creates a label for a tag with the given tag name. The label will have a tooltip showing the full tag name,
     * and will truncate the displayed text with an ellipsis if it exceeds 160 pixels in width.
     *
     * @param tagName The name of the tag to create a label for.
     * @return A {@code Label} representing the tag with the specified name.
     */
    private Label createTagLabel(String tagName) {
        Label tagLabel = new Label(tagName);
        tagLabel.setTooltip(new Tooltip(tagName));
        tagLabel.setMaxWidth(160);
        tagLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
        return tagLabel;
    }

    /**
     * Configures the wrapping of the label to allow text to wrap to the next line.
     */
    private void configureWrappingLabel(Label label) {
        label.setWrapText(true);
        label.setMaxWidth(Double.MAX_VALUE);
    }

    /**
     * Sets the label text if its value is present, otherwise hides the label.
     */
    private void setOptionalLabel(Label label, Optional<String> value, Function<String, String> formatter) {
        value.ifPresentOrElse(val -> {
            label.setText(formatter.apply(val));
            label.setVisible(true);
            label.setManaged(true);
        }, () -> {
            // Label is removed from layout
            label.setVisible(false);
            label.setManaged(false);
        });
    }
}
