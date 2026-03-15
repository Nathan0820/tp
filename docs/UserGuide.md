---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# BZNUS User Guide

BZNUS is a **desktop app for tracking customer contacts, food orders and customer-specific preferences.** It is **optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, BZNUS can get your contact management and order tracking tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-W09-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar bznus.jar` command to run the application.<br>
   A GUI similar to the following should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all customers.

   * `add n/John Doe p/98765432 a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the customer database.

   * `order 1  i/Pizza  q/3  at/2026-04-02 1200 a/123 Jurong West St 42, #05-01 s/PREPARING` : Adds an order for 3 pizzas to the 1st customer in the current list.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a customer: `add`

Adds a customer to the customer database.

Format: `add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [ig/IG] [tg/TELEGRAM] [a/ADDRESS] [t/TAG]…​`
<box type="tip" seamless>

**Tip:** A customer can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 a/John Street, Blk 123, #01-01`
* `add n/Betsy Crowe t/friend tg/@betsy a/Blk 456, Bedok North`
* `add n/Tech Corp SG e/orders@techcorp.sg a/Tech Tower, Level 12`

### Listing all customers : `list`

Shows a list of all customers in the address book.

Format: `list`

### Editing a customer : `edit`

Edits an existing customer in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [ig/IG] [tg/TELEGRAM] [a/ADDRESS] [t/TAG]…​`

* Edits the customer at the specified `INDEX`. The index refers to the index number shown in the displayed customer list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the customer will be removed i.e adding of tags is not cumulative.
* You can remove all the customer’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 a/John Street, Blk 123, #02-02` Edits the phone number and delivery address of the 1st customer to be `91234567` and `John Street, Blk 123, #02-02` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd customer to be `Betsy Crower` and clears all existing tags.

### Locating customers by name: `find`

Finds customers whose any of the field contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* All fields are searched.
* Only partial words will be matched e.g. `Han` will match `Hans`

Examples:
* `find John` returns `john` and `John Doe`
* `find 99272758` returns `Bernice Yu` as she had the number 99272758<br>
  ![result for 'find 99272758'](images/findBernice.png)

### Deleting a customer : `delete`

Deletes the specified customer from the customer database.

Format: `delete INDEX`

* Deletes the customer at the specified `INDEX`.
* The index refers to the index number shown in the displayed customer list.
* The index **must be a positive integer** 1, 2, 3, …​
* All orders associated with the deleted customer will also be removed.

Examples:
* `list` followed by `delete 2` deletes the 2nd customer in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st customer in the results of the `find` command.

### Adding an order: `order`

Adds a new order for a specific customer.

Format: `order INDEX i/ITEM_NAME q/QUANTITY at/DATE [a/DELIVERY_ADDRESS] [s/STATUS]`

* Adds an order to the customer at the specified `INDEX`.
* The index refers to the index number shown in the displayed customer list.
* The index **must be a positive integer** 1, 2, 3, …​
* `ITEM_NAME` should contain only alphanumeric characters and spaces, and cannot be blank.
* `QUANTITY` **must be a positive integer** 1, 2, 3, …​.
* `DATE` must be in `yyyy-mm-dd hhmm` format and must be a future date/time.
* If `DELIVERY_ADDRESS` is not provided, the customer's stored address will be used.
* If `STATUS` is not provided, it defaults to `PREPARING`. Valid statuses: `PREPARING`, `READY`, `DELIVERED`, `CANCELLED`.

**Examples:**
* `order 1 i/Pizza q/3 at/2026-04-02 1200`
* `order 2 i/Burger q/5 at/2026-03-15 1800 a/123 Jurong West St 42, #05-01`
* `order 3 i/Salad q/2 at/2026-04-10 1200 s/DELIVERED`

### Deleting an Order: `delete-o`

Deletes the specific order from the order database.

Format: `delete-o ORDER_INDEX`

* Deletes the order at the specified `ORDER_INDEX`.
* The order index refers to the index number shown in the displayed order list.
* The index **must be positive integers** 1, 2, 3, …​

**Examples:**
* `view-o ALL` followed by `delete 3` deletes the 3rd order in the results of the `view-o` command.
* `find-o i/pizza` followed by `delete 1` deletes the 1st order in the results of the `find-o` command.

### Search for Orders based on certain keywords: `find-o`
Search for different orders with 3 category options: item name, delivery address, customer id

Format `find-o Category-Type/Category-Keywords`

* Find the orders given the `Category-Keywords` from the `Category-Type`.
* The category keywords refer to the keyword used to look for orders.
* The category type refers to one of the 3 category options shown above.
* The category type **must be one of i/a/c**, which are respectively item, address, customer.

**Examples:**
* `find-o i/pizza` - Look for orders with item keyword "pizza"
* `find-o a/Ang Mo Kio` - Look for orders with delivery address "Ang Mo Kio"

### View Orders by status: `view-o`

View a list of orders by their status: (PREPARING/READY/DELIVERED/CANCELLED/ALL) 

Format: `view-o STATUS`

* Displays all orders that match the specified status.
* The status must be one of the following: `preparing`, `ready`, `delivered`, `cancelled`, or `all`.
* The command is case-insensitive (e.g., `PREPARING`, `Preparing`, `prEpaRIng` are all valid).

**Status Options:**

| Status | Meaning |
|--------|---------|
| `preparing` | Orders currently being prepared |
| `ready` | Orders ready for delivery |
| `delivered` | Orders that have been successfully delivered |
| `cancelled` | Orders that have been cancelled |
| `all` | All orders in the system |

**Examples:**

* `view-o preparing` - Look for all orders with status "preparing"
* `view-o ready` - Look for all orders with status "ready"
* `view-o delivered` - Look for all orders with status "delivered"
* `view-o cancelled` - Look for all orders with status "cancelled"
* `view-o all` - Look for all orders regardless of status

### Clearing all entries : `clear`

Clears all customers and their orders from BZNUS.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

BZNUS data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

BZNUS data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, BZNUS will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause BZNUS to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add Contact**    | `add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [ig/IG] [tg/TELEGRAM] [a/ADDRESS] [t/TAG]…​` <br> e.g., `add n/James Ho p/99996666 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/regular`
**Add Order**| `order INDEX i/ITEM_NAME q/QUANTITY at/DELIVERY_TIME [a/DELIVERY_ADDRESS] [s/STATUS]` <br> e.g., `order 3  i/Pizza  q/3  at/2026-04-02 1200 a/123 Jurong West St 42, #05-01 s/PREPARING`
**Clear**  | `clear`
**Delete Contact** | `delete INDEX` <br> e.g., `delete 3`
**Delete Order** | `delete-o ORDER_INDEX` <br> e.g., `delete-o 1`
**Edit Contact**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [ig/IG] [tg/TELEGRAM] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee ig/jamesLee`
**Exit**   | `exit`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
