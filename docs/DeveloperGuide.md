---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# BZNUS Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AddressBook-Level3 (AB3) project created by the [SE-EDU initiative](https://se-education.org).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div class="section-spacing">

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S2-CS2103T-W09-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2526S2-CS2103T-W09-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point).

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

</div>

<div class="section-spacing">

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `OrderListPanel`,`StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

</div>

<div class="section-spacing">

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, the user input is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. When the command is executed, it can communicate with the `Model` (e.g., to delete a person and all his/her orders).<br>
   For readability, the diagram omits preliminary interactions with the `Model` that occur before the delete operations (e.g., retrieving the currently displayed list of persons).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, `EditOrderCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

</div>

<div class="section-spacing">

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data: This includes all `Person` objects (contained in a `UniquePersonList` object) and all `Order` objects (contained in a `OrderList` object).
* stores the currently 'selected' `Person` objects and `Order` objects (e.g., results of a search query) as separate _filtered_ lists which are exposed to outsiders as unmodifiable `ObservableList<Person>` and `ObservableList<Order>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

</div>

<div class="section-spacing">

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

</div>

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

<div class="section-spacing">

### Order management

The `Model` component manages `Order` entities using an `OrderList`, which stores all orders in the address book. Each `Order` records the customer’s `UUID` rather than holding a direct reference to a `Person` object. `Person` objects are replaced wholesale when edited, so storing a reference would become outdated. Using `UUID` keeps orders stable and avoids cascading updates when customer details change.

An `Order` stores the following fields:
* Customer’s `UUID`
* `Item`
* `Quantity`
* `DeliveryTime`
* `Address`
* `Status`

These fields (except the customer’s `UUID`) are implemented as domain classes, allowing each to encapsulate its own validation and formatting logic. Optional fields, `Address` and `Status`, allow the system to fall back to the customer’s saved address or a default status when these values are not provided by the user.

`OrderList` wraps an internal `ObservableList<Order>`, ensuring that UI components automatically update whenever orders are added or modified. This design integrates the new `Order` entity into the existing model while minimizing coupling.

</div>

<div class="section-spacing">

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

</div>

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

<div class="section-spacing">

### Product scope

**Target user profile**:

* is a home-based food and beverage (F&B) business owner
* has a need to manage a significant number of customer contacts from multiple messaging platforms, complex dietary requirements, and food orders with varying deadlines
* prefers desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI (Command Line Interface) apps

**Value proposition**: Manage customer profiles and track food orders significantly faster than a typical mouse/GUI-driven app.

</div>

<div class="section-spacing">

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                      | I want to …​                                                                                                 | So that I can…​                                                                                                                          |
|----------|------------------------------|--------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------|
| `* * *`  | First-time user              | Add a customer with their name and at least one contact field (phone, email, Facebook, Instagram or address) | Maintain a centralized database of my customers regardless of which platform they use to contact me                                      |
| `* * *`  | User                         | Delete customer profiles                                                                                     | Remove customers who no longer order from me and keep my customer database clean                                                         |
| `* * *`  | Seller with many customers   | View a list of all my customers                                                                              | View my customer base at a glance                                                                                                        |
| `* * *`  | User                         | Add new food orders for a specific customer (item, quantity, time, destination, status)                      | Record new orders as they arrive from different message platforms                                                                        |
| `* * *`  | User                         | Delete food orders by a specific customer                                                                    | Keep my records updated when a customer cancels their order                                                                              |
| `* * *`  | Conscientious seller         | View a specific customer's order history alongside their contact details                                     | Quickly understand their past preferences and current pending requests before responding to their messages                               |
| `* * *`  | Busy seller with many orders | View a list of all upcoming food orders across my entire customer base                                       | Plan my order preparation schedule and ensure no orders are missed during peak periods                                                   |
| `**`     | Seller with many customers   | Search for specific customers by name                                                                        | Quickly retrieve customer details without scrolling through a long list                                                                  |
| `**`     | Seller with many customers   | Search for customers by their phone number, Facebook username, or Instagram handle                           | Quickly identify a returning customer even if I only have their social media handle or phone number                                      |
| `**`     | User                         | Edit customer details including their name, phone number, delivery address or social media handles           | Update addresses or contact numbers when they change                                                                                     |
| `**`     | User                         | Edit existing order details for any customer                                                                 | Keep my records updated when a customer edits their request                                                                              |
| `**`     | Conscientious seller         | Categorize customers by type (e.g., Corporate, Regular, New)                                                 | Tailor my marketing efforts based on customer type to build long-term relationships                                                      |
| `**`     | Conscientious seller         | Add special notes for each user (“prefers weekend delivery”, “no chilli” etc)                                | Deliver a more personalised service                                                                                                      |
| `**`     | Conscientious seller         | Record the dietary restrictions of each customer (e.g., vegan, no peanuts)                                   | Avoid preparing products that are potentially harmful for them                                                                           |
| `*`      | User                         | Upload a profile picture for a customer contact                                                              | Visually verify a customer's identity during order handovers and reduce the risk of record-entry errors for customers with similar names |
| `*`      | User                         | Store contact details of ingredient suppliers separately                                                     | Maintain a clear separation between my customers and my ingredient providers                                                             |

</div>

<div class="section-spacing">

### Use cases

(For all use cases below, the **System** is `BZNUS` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add Customer**

**Guarantees:**
* If the command succeeds, exactly one new customer with a unique name is added to the system.
* If the command fails at any point, no customer is added and the system state remains unchanged.

**MSS:**

1. User enters the add customer command with a name and any optional fields (e.g. contact details, remark, tags).

2. BZNUS validates the input.

3. BZNUS checks that the customer name is unique.

4. BZNUS adds the customer to the system.

5. BZNUS shows a success message with the added customer's details and displays the full list of customers.

   Use case ends.

**Extensions:**

* 2a. BZNUS detects invalid or missing fields (e.g. empty name, no contact method, or invalid field format).

  * 2a1. BZNUS displays an error message indicating the issue found.
  
  * 2a2. User updates the entered details.

    Use case resumes from step 2.
  
* 3a. BZNUS detects that the customer name provided matches that of an existing customer (case-insensitive).

  * 3a1. BZNUS displays an error message indicating the issue found.

  * 3a2. User updates the entered details.
    
    Use case resumes from step 2.

---

**Use case: UC02 - Delete Customer**\
**Guarantees:**
* If the deletion cannot be completed (e.g. invalid customer index), the system does not remove any customer.
* Only customers that are currently displayed can be deleted.

**MSS:**

1. User enters the delete customer command along with the specified customer.

2. BZNUS deletes the customer from the system.

3. BZNUS deletes all orders associated with the customer from the system.

4. BZNUS shows a success message to show the customer is deleted.

5. BZNUS updates the displayed list to exclude the deleted customer.

   Use case ends.

**Extensions:**

* 1a. BZNUS detects invalid or missing fields.

    * 1a1. BZNUS displays an error message.

    * 1a2. User updates the entered details.

  Steps 1a1-1a2 are repeated until the information entered is valid.

  Use case resumes from step 2.

---

**Use case: UC03 - Edit Customer**

**Preconditions:**
* At least one customer is present in the currently displayed customer list.

**Guarantees:**
* If the command succeeds, the customer's data is updated as requested and saved to storage.
* If the command fails at any point, the customer's data remains unchanged. 
* Only customers that are currently displayed can be edited.

**MSS:**

1. User enters the edit customer command with a customer index.

2. BZNUS validates the input (e.g. at least one field to edit, valid field formats, and valid index).

3. BZNUS verifies post-edit constraints (customer name remains unique, and at least one contact method remains for the edited customer).

4. BZNUS saves the updated details of the specified customer.

5. BZNUS shows a success message with the customer's updated details and displays the full list of customers.

   Use case ends.

**Extensions:**

* 2a. BZNUS detects invalid user input (e.g. invalid index, no fields supplied, empty name, or invalid field value).

    * 2a1. BZNUS shows an error message indicating the issue found.
  
    * 2a2. User updates the entered details.

        Use case resumes from step 2.

* 3a. BZNUS detects a post-edit constraint violation (duplicate customer name, or all contact methods cleared for the customer).

    * 3a1. BZNUS shows an error message indicating the issue found. 

    * 3a2. User updates the entered details.

        Use case ends resumes from step 2.

---

**Use case: UC04 - Search customer information**

**MSS:**

1. User enters search keywords (e.g., name, phone number, delivery address).

2. BZNUS retrieves and displays a list of customer profiles matching the keywords.

   Use case ends.

**Extensions:**

- 1a. BZNUS detects no matching customers for the entered keyword.

    - 1a1. BZNUS displays a message indicating no results were found.

      Use case ends.

---

**Use case: UC05 - Add order**\
**Guarantees:**
* The system records the order only if the provided order information is valid.

**MSS:**

1. User enters the order command along with the order details.

2. BZNUS stores the new order.

3. BZNUS shows a success message to indicate the order is added.

4. BZNUS updates the displayed list to include the order.

   Use case ends.

**Extensions:**

* 1a. BZNUS detects invalid or missing fields.

    * 1a1. BZNUS displays an error message.

    * 1a2. User updates the entered details.

    Steps 1a1-1a2 are repeated until the information entered is correct.

    Use case resumes from step 2.

---

**Use case: UC06 - Delete order**\
**Guarantees:**
* If the deletion cannot be completed (e.g. invalid order index, order not found), the system does not remove any order.
* Only orders that are currently displayed can be deleted.

**MSS:**

1. User enters the delete order command along with the order to be deleted.

2. BZNUS removes the specified order from the system.

3. BZNUS shows a success message to indicate the order is deleted.

4. BZNUS updates the displayed list to exclude the deleted order.

   Use case ends.

**Extensions:**

* 1a. BZNUS detects invalid or missing fields.

    * 1a1. BZNUS displays an error message.

    * 1a2. User updates the entered details.

    Steps 1a1-1a2 are repeated until the information entered is valid.

    Use case resumes from step 2.

</div>

<div class="section-spacing">

### Non-Functional Requirements

#### Portability Requirements
1. The system should work on any _mainstream OS_ with Java `17` or above installed. Users should not need to install additional runtime dependencies.

#### Performance Requirements
1. The system should support up to **1000 customers** and **5000 orders** without noticeable sluggishness in performance for typical usage.
2. For all valid commands, results should be displayed within **2 seconds** of submitting the command.

#### Scalability Requirements
1. The system should allow future addition of features such as data archiving and undo/redo without requiring major refactoring of existing code.
2. The system should allow future support for additional contact fields and order details (e.g. Telegram handle, delivery instructions) without requiring major refactoring of existing code.

#### Usability Requirements
1. A user with above-average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using mouse-driven interactions.
2. The interface should be intuitive enough that a home-based F&B seller with basic computer literacy can use the system without requiring extensive training.
3. Error messages should clearly describe the issue detected and, where practical, suggest how to correct it.

#### Reliability Requirements
1. The system should handle invalid user inputs gracefully by showing clear error messages instead of crashing.
2. The system must not corrupt or partially update customer or order data in the event of an invalid command or unexpected failure.
3. All changes to customer and order data should be saved to disk immediately after a successful data-modifying command.

#### Data Requirements
1. All customer and order data should be stored locally in a human-editable JSON file.
2. The storage format should remain readable in a standard text editor at the maximum expected data size (1000 customers, 5000 orders).

#### Security & Privacy Requirements
1. The system should not transmit customer or order data to external services during normal operation. Customer data should remain on the user's local machine unless the user explicitly copies or shares the data file.
2. The system should fail safely on corrupted storage data (e.g. recover to a safe state) instead of executing undefined behavior.

#### Testability Requirements
1. Core logic (parsing, command execution, and model updates) should be testable without launching the GUI.
2. Command outcomes should be deterministic for the same input and initial state, enabling reliable automated tests.

#### Maintainability Requirements
1. The codebase should be organized in a modular fashion with clear separation of concerns (e.g. UI, logic, model, storage) to facilitate future enhancements and debugging.
2. The codebase should follow a consistent coding style (e.g. Java coding conventions) to support easy onboarding of new contributors.

#### Constraints
1. The system should be a standalone desktop application and must not depend on a remote server or external database.

</div>

### Glossary

* **API (Application Programming Interface)**: The set of methods and interfaces provided by a component for other components to interact with it.
* **CLI (Command Line Interface)**: A text-based user interface used to interact with the software by typing commands.
* **DeliveryTime**: The scheduled date and time at which an order should be delivered.
* **GUI (Graphical User Interface)**: A visual interface that allows users to interact with the software through graphical elements like windows and buttons.
* **Home-based F&B seller**: The primary user of BZNUS, an individual running a small-scale food and beverage operation from their home.
* **Item**: Represents the name of the product being ordered.
* **JSON (JavaScript Object Notation)**: A lightweight, text-based, human-readable format used for storing the application's data locally.
* **Mainstream OS**: Windows, Linux, Unix, macOS
* **Order**: A customer’s request for an item, including quantity, delivery details, and status.
* **OrderList**: An internal data structure that stores and manages all `Order` objects in the system.
* **Person**: Represents a customer in BZNUS. Each `Person` stores the customer’s contact details and is associated with `Order` objects via a `UUID`.
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Quantity**: The number of items ordered. Must be a valid positive integer.
* **Status**: The current state of an order, restricted to one of `PREPARING`, `READY`, `DELIVERED`, or `CANCELLED`. Defaults to `PREPARING` if not specified.
* **Tag**: A customizable, color-coded label assigned to a customer to quickly identify specific traits, preferences, or dietary restrictions (e.g., "vegan", "VIP", "corporate").
* **UUID (Universally Unique Identifier)**: A unique identifier assigned to each customer, used to associate an order with a specific customer without storing a direct reference.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

<div class="section-spacing">

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder.

   2. Double-click the jar file. Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

</div>

<div class="section-spacing">

### Adding a customer

1. Adding a customer with **all fields**

   1. Prerequisites: No existing customer named "David Tan".

   2. Test case: `add n/David Tan p/98765432 fb/david.tan ig/david.tan a/Blk 123, #01-01 t/vegetarian r/Prefers weekend delivery`<br>
      Expected: Customer named "David Tan" is added with all specified fields. Success message shown.

2. Adding a customer with **only the required fields** (name and at least one contact method)

   1. Prerequisites: No existing customer named "Alice Tan".

   2. Test case: `add n/Alice Tan p/98765432`<br>
      Expected: Customer named "Alice Tan" is added. Success message shown.

3. Adding a customer with **no contact method provided**

   1. Prerequisites: No existing customer named "John Tan".

   2. Test case: `add n/John Tan `<br>
      Expected: Command fails with an error message indicating that at least one contact method must be provided. No customer added. <br>

4. Adding a customer with **invalid field format**

   1. Prerequisite: No existing customer named "John Tan".
   
   2. Test case: `add n/John Tan p/phone` <br>
      Expected: Command fails with an error message indicating that the phone number must be 8–15 digits and contain only numbers. No customer added.

5. Adding a customer **without a name** 

   1. Prerequisite: No existing customer named "John Tan". 
   
   2. Test case: `add ig/john.tan` <br>
      Expected: Command fails with an invalid command format error. No customer added.

6. Adding a customer with a **duplicate name**

   1. Prerequisites: A customer named "David Tan" already exists.

   2. Test case: `add n/David Tan p/92345678`<br>
      Expected: Command fails with an error message indicating that a customer with the same name already exists. No customer added.

7. Optional persistence check

   1. After any successful add, close and relaunch the app.<br>
      Expected: Newly added customer remains.

</div>

<div class="section-spacing">

### Editing a customer

1. Editing a customer with an **updated field value**

   1. Prerequisite: List all customers using the `list` command. At least one customer exists in the list.
   
   2. Test case: `edit 1 p/91234567` <br>
      Expected: First displayed customer's phone number is updated to 91234567. Success message shown.

2. Editing a customer by **clearing an optional field**

   1. Prerequisite: First displayed customer has Instagram.
   
   2. Test case: `edit 1 ig/`<br>
      Expected: First displayed customer's Instagram is cleared. Success message shown.
   
3. Editing a customer by **clearing multiple optional fields but keeping one contact method**

   1. Prerequisite: First displayed customer has at least one contact method.
   
   2. Test case: `edit 1 p/ fb/ ig/ a/Blk 123 Clementi Ave 2`<br>
      Expected: First displayed customer's phone/Facebook/Instagram are cleared, with address set to "Blk 123 Clementi Ave 2". Success message shown.
   
4. Editing a customer by **clearing all contact methods**

   1. Prerequisite: First displayed customer has at least one contact method.
   
   2. Test case: `edit 1 p/ fb/ ig/ a/`<br>
      Expected: Command fails with an error message indicating that at least one contact method must remain. No changes applied.
   
5. Editing a customer **without providing any fields** to edit

   1. Test case: `edit 1`<br>
      Expected: Command fails with an error message indicating that at least one field must be provided. No changes applied.

6. Editing a customer with **invalid index format** (non-positive integer)

   1. Test case: `edit 0 p/91234567`<br>
      Expected: Command fails with an invalid command format error (index must be a positive integer). No changes applied.

7. Editing a customer with an **invalid index value** (index larger than the number of customers displayed)

   1. Prerequisite: Less than 20 customers exist in the displayed customer list.
   
   2. Test case: `edit 20 p/91234567`<br>
      Expected: Command fails with an error message indicating that the supplied index is invalid. No changes applied.

8. Editing a customer with **invalid field format**
   
   1. Test case: `edit 1 fb/.abc`<br>
      Expected: Command fails with an error message indicating the Facebook username requirements. No changes applied.

9. Editing a customer by providing a **duplicate name** (case-insensitive)

   1. Prerequisite: An existing customer that is not being edited has the name "Bernice Yu".
         
   2. Test case: `edit 1 n/bernice yu`<br>
      Expected: Command fails with an error message indicating that a customer with the same name already exists. No changes applied.
         
10. Optional persistence check

    1. After any successful edit, close and relaunch the app.<br>
       Expected: Edited customer details remain.

</div>

<div class="section-spacing">

### Deleting a customer

1. Deleting a customer while all customers are being shown

   1. Prerequisites: List all customers using the `list` command. Ensure that there are multiple customers in the list.

   2. Test case: `delete 1`<br>
   Expected: The first customer is deleted from the list. A message containing the details of the deleted customer is shown.

   3. Test case: `delete 0`<br>
      Expected: No customer is deleted. An error message is shown.

   4. Other incorrect delete customer commands to try:
      * `delete`
      * `delete x` (where x is larger than the list size)
      * `delete -1`
      * `delete abc`
      * `delete 1.5`<br><br>

      Expected: No customer is deleted. An error message is shown.

2. Deleting a customer from a filtered list
   
   1. Prerequisites: Run a filtering command such as `find <keyword>` to filter the customer list. The filtered list should contain at least one customer.

   2. Test case: `delete 1`<br>
   Expected: The first customer in the filtered list is deleted. A message containing the details of the deleted customer is shown.

   3. Test case: `delete x` (where x is larger than the filtered list size)<br> 
   Expected: No customer is deleted. An error message is shown.

3. Deleting a customer with associated orders
      
   1. Prerequisites: The customer to be deleted has one or more associated orders. Ensure the customer's orders are visible using the `list-o` command or by selecting the customer.

   2. Test case: `delete 1`<br>
   Expected: The first customer in the displayed list is deleted. All orders associated with that customer are also deleted. A message containing the details of the deleted customer is shown.

</div>

<div class="section-spacing">

### Adding an order

1. Adding an order while all customers are being shown

   1. Prerequisites: List all customers using the `list` command. Ensure that there is at least one customer in the list.

   2. Test case: `order 1 i/Pizza q/3 at/2026-05-01 1400 a/123 Clementi Rd s/PREPARING`<br>
   Expected: A new order is added for the first customer. A message containing the details of the order is shown.

   3. Test case: `order 0 i/Pizza q/3 at/2026-05-01 1400 a/123 Clementi Rd s/PREPARING`<br>
   Expected: No order is added. An error message is shown.

   4. Other incorrect add order commands to try:
      * `order`
      * `order 1`
      * `order 1 i/ q/3 at/2026-05-01 1400`
      * `order 1 i/Pizza q/-5 at/2026-05-01 1400`
      * `order 1 i/Pizza q/3 at/2026/05/01 1400`
      * `order 1 i/Pizza q/3 at/2026-13-01 1400`
      * `order 1 i/Pizza q/3 at/2026-05-01 1400 s/SHIPPED`<br><br>

      Expected: No order is added. An error message is shown.

2. Adding an order while the customer list is filtered

   1. Prerequisites: Run a filtering command such as `find <keyword>` to filter the customer list. The filtered list should contain at least one customer.

   2. Test case: `order 1 i/Pizza q/3 at/2026-05-01 1400 a/123 Clementi Rd s/PREPARING`<br>
   Expected: A new order is added for the first customer in the filtered list. A message containing the details of the order is shown.

   3. Test case: `order x i/Pizza q/3 at/2026-05-01 1400 a/123 Clementi Rd s/PREPARING` (where x is larger than the filtered list size)<br>
   Expected: No order is added. An error message is shown.

</div>

<div class="section-spacing">

### Deleting an order

1. Deleting an order while all orders are being shown

   1. Prerequisites: List all orders using the `list-o` command. Ensure that there are multiple orders in the list.

   2. Test case: `delete-o 1`<br>
   Expected: The first order is deleted from the list. A message containing the details of the deleted order is shown.

    3. Test case: `delete-o 0`<br>
      Expected: No order is deleted. An error message is shown.

    4. Other incorrect delete order commands to try:
       * `delete-o`
       * `delete-o x` (where x is larger than the list size)
       * `delete-o -1`
       * `delete-o abc`
       * `delete-o 1.5`<br><br>

       Expected: No order is deleted. An error message is shown.

2. Deleting an order from a filtered list

   1. Prerequisites: Run a filtering command such as `find-o <category>/<keyword>` to filter the order list. The filtered list should contain at least one order.

   2. Test case: `delete-o 1`<br>
   Expected: The first order in the filtered list is deleted. A message containing the details of the deleted order is shown.

   3. Test case: `delete-o x` (where x is larger than the filtered list size)<br>
   Expected: No order is deleted. An error message is shown.

</div>

<div class="section-spacing">

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

</div>

## **Appendix: Planned Enhancements**

Team size: 5

1. **Add non-blocking warnings for duplicate contact details**: Currently, BZNUS only checks for duplicate customer names. As a result, users may accidentally create duplicate entries for the same customer with the same phone number or social media handle. We plan to display a non-blocking warning when a new or edited customer shares the same phone number, Facebook username, or Instagram handle as existing customer(s). The warning will highlight the matching contact fields and may list customers with overlapping details to help users decide whether the new or edited entry is intentional. This helps users spot potential duplicates early without blocking legitimate entries (e.g. shared contact details among family members or corporate customers).

2. **Allow editing of the customer linked to an existing order**: Currently, once an order is created, the customer associated with it cannot be changed. This is inconvenient when a user accidentally selects the wrong customer. We plan to extend the edit order command to support updating the customer the order is linked to. The system will validate that the new customer exists and update the order accordingly. This enhancement addresses the flaw where users must delete and recreate an order to correct a customer assignment.

3. **Add a confirmation step before deleting a customer or an order**: Deleting a customer or an order currently executes immediately, which increases the risk of accidental data loss. We plan to introduce a confirmation prompt (e.g., “Are you sure you want to delete this customer? (yes/no)”). The command will only proceed if the user explicitly confirms. This enhancement prevents accidental deletions and improves data safety.

## **Appendix: Effort**

Compared with AB3, BZNUS required substantially more effort because it extends a single-entity contact manager into a multi-entity system with linked `Customer` and `Order` workflows. The added complexity came from coordinating model design, validation, persistence, and UI updates across two related domains instead of one.

### Scope expansion and technical complexity

We implemented customer and order flows end-to-end (parse → validate → execute → persist → display). This involved refactoring AB3 classes and introducing new components such as command classes (e.g. `AddOrderCommand`, `DeleteOrderCommand`), parsers (e.g. `FindOrderCommandParser`), order-domain model classes (e.g. `DeliveryTime`, `OrderList`), storage adapters (e.g. `JsonAdaptedOrder`), and order-related UI components (e.g. `OrderCard`, `OrderListPanel`).

A key design challenge was maintaining customer-order referential integrity. We eventually adopted UUID-based linkage (`Person#getId()` and `Order#getCustomerId`) so that orders could be associated with customers without depending on displayed list indices. This supported important behaviours such as:

* cascading deletion of a customer's orders when the customer is deleted,
* adding an order that uses the customer's stored address when no delivery address is specified, while still failing safely if no address is found.

We also spent significant effort redesigning the UI to present customer and order information clearly in separate panels. Notably, we implemented a click-to-filter functionality, where selecting a customer card updates the order panel to show only that customer's orders.

### Main implementation challenges

The most effort-intensive parts of the project were:

* designing and maintaining customer-order relationships without breaking data consistency,
* enforcing stronger domain validation (e.g. optional-field constraints and contact-method rules),
* keeping command parsing and validation consistent across add/edit/delete/find operations for both entities,
* updating JSON storage so customers and orders could be saved and restored reliably,
* adjusting the UI to keep customer and order information readable and well-organised,
* writing and updating automated tests to cover boundary cases, invalid input, cross-entity interactions, and persistence behaviour,
* updating the User Guide (UG) and Developer Guide (DG) to provide clear instructions and documentation for our expanded feature set.

In addition to implementation work, we spent time coordinating developmental changes through the forking workflow, issue tracking, and thorough pull-request reviews. These helped us identify bugs early and avoid unintended side effects when multiple features touched shared components.

### Code Reuse

BZNUS reused AB3's architectural foundation, including its component structure, command workflow, parser style, JavaFX scaffold, and JSON-based storage approach. This reduced setup effort and allowed us to focus on BZNUS-specific features such as customer-order management, custom validation rules, and richer user-facing workflows.

We also reused AB3-supported libraries and tools, such as JavaFX, Jackson and JUnit. This avoided additional integration work and kept the project focused on domain logic.

### Achievements

Our key achievements include:

* **A complete multi-entity application**, with customer and order management integrated into a coherent CLI workflow,
* **A responsive and intuitive UI** that dynamically updates to show relevant customer and order information while maintaining readability and structure,
* **Helpful, user‑friendly error messages** that guide users toward correcting invalid inputs and reduce friction during command entry.
* **A maintainable and extensible codebase**, supported by modular design, clear separation of concerns, and well‑documented components.
* **Comprehensive automated testing**, covering validation rules, cross‑entity interactions, and persistence behaviour to ensure long-term code reliability.
* **Clear and user‑focused documentation**, with updated UG/DG sections, diagrams, and testing instructions that reflect the expanded feature set.
