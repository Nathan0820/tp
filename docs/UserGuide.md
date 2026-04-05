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


## Table of Contents

1. [Quick start](#quick-start)
2. [Features](#features)
   - [Viewing help: `help`](#viewing-help)
3. [Customer Commands](#customer-commands)
   - [Adding a customer: `add`](#add)
   - [Listing all customers: `list`](#list)
   - [Editing a customer: `edit`](#edit)
   - [Finding customers: `find`](#find)
   - [Deleting a customer: `delete`](#delete)
4. [Order Commands](#order-commands)
   - [Adding an order: `order`](#order)
   - [Finding orders: `find-o`](#find-o)
   - [Editing an order: `edit-o`](#edit-o)
   - [Listing all orders: `list-o`](#list-o)
   - [Deleting an order: `delete-o`](#delete-o)
5. [Other Commands](#other-commands)
   - [Clearing all entries: `clear`](#clear)
   - [Exiting the program: `exit`](#exit)
6. [Data Storage](#data-storage)
   - [Saving the data](#saving-data)
   - [Editing the data file](#edit-data)
   - [Archiving data files `[coming in v2.0]`](#archive-data)
7. [FAQ](#faq)
8. [Known issues](#known-issues)
9. [Command summary](#command-summary)
   - [Customer Commands](#c-command)
   - [Order Commands](#o-command)
   - [Other Commands](#others)

--------------------------------------------------------------------------------------------------------------------

## <a id="quick-start"></a>Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-W09-3/tp/releases).

3. Copy the `.jar` file to the folder you want to use as the _home folder_ for BZNUS.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar bznus.jar` command to run the application.<br>
   A GUI similar to the following should appear in a few seconds. Note how the app contains some sample data.<br>\
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all customers.

   * `add n/John Doe p/98765432 a/John street, block 123, #01-01` : Adds a customer named `John Doe` to the customer database.

   * `order 1  i/Pizza  q/3  at/2026-06-02 1200 a/123 Jurong West St 42, #05-01 s/PREPARING` : Adds an order for 3 pizzas to the 1st customer in the current list.

   * `delete 3` : Deletes the 3rd customer shown in the current list.

   * `clear` : Deletes all customers and their orders.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## <a id="features"></a>Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines. Space characters surrounding line-breaks may be omitted when copied over to the application.
  * **Solution:** Type the command manually if pasting doesn't work.

</box>

### <a id="viewing-help"></a>Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

--------------------------------------------------------------------------------------------------------------------

## <a id="customer-commands"></a>Customer Commands

<div class="section-spacing">

### <a id="add"></a>Adding a customer : `add`

Adds a customer to the customer database.

Format: `add n/NAME [p/PHONE] [ig/INSTAGRAM] [fb/FACEBOOK] [a/ADDRESS] [r/REMARK] [t/TAG]…​`

* `NAME` is mandatory. It must contain only alphanumeric characters, spaces, and apostrophes (e.g. Mary O'Connor). It cannot be blank.
* `PHONE` must be 8 to 15 digits long and contain only numbers (e.g. 91234567 or 60123456789). No spaces, '+' sign, or other symbols are allowed.
* `INSTAGRAM` must be 1 to 30 characters long and contain only letters, numbers, underscores, and periods. It must not end with a period or have consecutive periods. No internal whitespaces allowed. The `@` prefix is optional.
* `FACEBOOK` must be 5 to 50 characters long and contain only letters, numbers, and periods. It must not have leading, trailing, or consecutive periods. No internal whitespaces allowed. The `@` prefix is optional.
* `ADDRESS` can be any non-blank string.
* `REMARK` can be any non-blank string.
* `TAG` must contain at least one letter or number, and may include spaces, underscores, and hyphens.

<box type="important" seamless>

**Note:** A customer must have **at least one** contact method (`p/PHONE`, `ig/INSTAGRAM`, `fb/FACEBOOK` or `a/ADDRESS`). The command will fail and show an error message if all contact methods are missing.

</box>

<box type="important" seamless>

**Duplicate Handling:** Customer names are unique (case-insensitive). For example, "John Doe" and "john doe" are considered the same person, and the app will reject the duplicate entry. Different customers may share contact details (e.g. phone, Facebook, Instagram, or address).

</box>

<box type="tip" seamless>

**Tip:** If you have two customers with the same name, use descriptors to differentiate them (e.g. "**John Doe (Clementi)**" and "**John Doe (Jurong)**"). A customer can also have any number of tags (including 0).

</box>

Examples:
* `add n/John Doe p/98765432 ig/john a/John Street, Blk 123, #01-01 r/prefers weekend delivery t/VIP t/regular`
* `add n/Betsy Crowe t/friend fb/betsy.crowe a/Blk 456, Bedok North r/allergic to peanuts`
* `add n/Tech Corp SG p/67778888 ig/techcorp.sg a/Tech Tower, Level 12 r/Invoicing required`

</div>

<div class="section-spacing">

### <a id="list"></a>Listing all customers : `list`

Shows a list of all customers in the customer database.

Format: `list`

</div>

<div class="section-spacing">

### <a id="edit"></a>Editing a customer : `edit`

Edits an existing customer in the displayed customer list.

Format: `edit INDEX [n/NAME] [p/PHONE] [ig/INSTAGRAM] [fb/FACEBOOK] [a/ADDRESS] [r/REMARK] [t/TAG]…​`

* Edits the customer at the specified `INDEX`. The index refers to the index number shown in the displayed customer list. The index **must be a positive integer** 1, 2, 3, …​
* At least one field to edit must be provided.
* Existing values will be replaced by the values you provide.
* You can clear the following single-valued fields by providing the prefix without a value:
  * `p/` clears phone
  * `ig/` clears Instagram
  * `fb/` clears Facebook
  * `a/` clears address
  * `r/` clears remark
* `n/` (name) cannot be empty if present. Use `n/NEW_NAME` to change the name.
* After the edit is applied, the customer must still have at least one contact method (`p/`, `ig/`, `fb/`, or `a/`). Otherwise, the edit is rejected.
* Tags are handled as a set:
  * t/TAG [t/MORE_TAGS]...` replaces all the customer's existing tags with the tag(s) provided. I.e. the addition of tags is not cumulative.
  * `t/` clears all existing tags.

Examples:
*  `edit 1 p/91234567 a/John Street, Blk 123, #02-02` Edits the phone number and delivery address of the 1st customer to be `91234567` and `John Street, Blk 123, #02-02` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd customer to be `Betsy Crower` and clears all existing tags.
*  `edit 3 ig/ r/` Clears Instagram and remark for the 3rd customer.
*  `edit 4 p/ fb/ ig/ a/` Fails as this would remove all contact methods from the 4th customer.

</div>

<div class="section-spacing">

### <a id="find"></a>Finding customers : `find`

Finds customers whose details match the given keywords. You can search across all fields or target a specific field using prefixes.

#### General Search
Format: `find KEYWORD`

* The search is case-insensitive. e.g. `hans` will match `Hans`.
* All fields are searched.
* Partial matches are supported e.g. `Han` will match `Hans`.

Examples:
* `find John` returns `john` and `John Doe`
* `find 99272758` returns `Bernice Yu` if her contact details contains these digits<br>\
  ![result for 'find 99272758'](images/findBernice.png)

<box type="important" seamless>

**Note:** All text after `find` is treated as a single keyword. For example, `find John Doe` searches for the phrase “John Doe” as one keyword, not two separate keywords.

</box>

#### Specific Field Search
Format: `find PREFIX/KEYWORD`

* The search is case-insensitive. e.g. `hans` will match `Hans`.
* Limits the search to a single specified field.
* Allows searching with multiple prefixes.

Available Prefixes:
* `n/NAME`
* `p/PHONE`
* `fb/FACEBOOK`
* `ig/INSTAGRAM`
* `a/ADDRESS`
* `r/REMARK`
* `t/TAG`

Examples:
* `find n/Alice` returns all customers whose name contains `Alice`.
* `find t/regular` returns all customers whose tags contain `regular`.
* `find n/Bob r/non-spicy` returns all customers whose name contains `Bob` and whose remark contains `non-spicy`.

</div>

### <a id="delete"></a>Deleting a customer : `delete`

Deletes the specified customer from the customer database.

Format: `delete INDEX`

* Deletes the customer at the specified `INDEX`.
* The index refers to the index number shown in the displayed customer list.
* The index **must be a positive integer** 1, 2, 3, …​

<box type="important" seamless>

**Note:** All orders associated with the deleted customer will also be deleted.

</box>

Examples:
* `list` followed by `delete 2` deletes the 2nd customer in the customer database.
* `find Betsy` followed by `delete 1` deletes the 1st customer in the results of the `find` command.

---

## <a id="order-commands"></a>Order Commands

<div class="section-spacing">

### <a id="order"></a>Adding an order : `order`

Adds a new order for a specific customer.

Format: `order INDEX i/ITEM_NAME q/QUANTITY at/DELIVERY_TIME [a/DELIVERY_ADDRESS] [s/STATUS]`

* Adds an order to the customer at the specified `INDEX`.
* The index refers to the index number shown in the displayed customer list.
* The index **must be a positive integer** 1, 2, 3, …​
* `ITEM_NAME` must contain only alphanumeric characters and spaces, and cannot be blank.
* `QUANTITY` **must be a positive integer** 1, 2, 3, …​.
* `DELIVERY_TIME` must be in `yyyy-mm-dd hhmm` format and must be a future date/time.
* If `DELIVERY_ADDRESS` is not provided, the customer's stored address will be used.\
If the customer has no stored address, you will be prompted to enter a delivery address for the order.
* If `STATUS` is not provided, it defaults to `PREPARING`. Valid statuses: `PREPARING`, `READY`, `DELIVERED`, `CANCELLED`.

<box type="tip" seamless>

**Tip:** You can specify a/PICKUP for pickup orders.

</box>

**Examples:**
* `order 1 i/Pizza q/3 at/2026-06-02 1200`
* `order 2 i/Burger q/5 at/2026-07-15 1800 a/123 Jurong West St 42, #05-01`
* `order 3 i/Salad q/2 at/2026-08-10 1200 s/DELIVERED`

</div>

<div class="section-spacing">

### <a id="find-o"></a>Finding orders : `find-o`

Search for different orders with 4 category options: item name, delivery address, customer id, status

Format: `find-o Category-Type/Category-Keywords`

* Find the orders given the `Category-Keywords` from the `Category-Type`.
* The category keywords refer to the keyword used to look for orders.
* The category type refers to one of the 4 category options shown above.
* The category type **must be one of i/a/c/s**, which are respectively item, address, customer, status.
* Allows searching with multiple prefixes.

**Examples:**
* `find-o i/pizza` - Look for orders with item keyword "pizza"
* `find-o a/Ang Mo Kio` - Look for orders with delivery address "Ang Mo Kio"
* `find-o s/Delivered` - Look for orders that are already delivered
* * `find-o i/burger a/Kent Ridge` - Look for orders with item keyword "burger" and delivery address "Kent Ridge"

<box type="tip" seamless>

**Tip:** Besides using `c/`, you can also click on a customer entry in the list to view all orders of that customer.
![Click a customer entry to view all orders](images/customer-click-view-orders.png)

</box>

</div>

<div class="section-spacing">

### <a id="edit-o"></a>Editing an order : `edit-o`

Updates fields of an existing order. Any field you specify replaces the previous value; other fields stay unchanged.

Format: `edit-o ORDER_INDEX [i/ITEM_NAME] [q/QUANTITY] [at/DELIVERY_TIME] [a/DELIVERY_ADDRESS] [s/STATUS]`

* Edits the order at the specified `ORDER_INDEX`. The index refers to the order number shown in the **currently displayed order list**. The index **must be a positive integer**
* **At least one** of `i/`, `q/`, `at/`, `a/`, or `s/` must be provided. Omitting all of them is not allowed.
* The order **stays with the same customer**; you cannot reassign an order to another customer with this command.
* Field rules are the same as when using **`order`** (see **Adding an order** above):
  * `ITEM_NAME` should contain only alphanumeric characters and spaces, and cannot be blank.
  * `QUANTITY` **must be a positive integer** 1, 2, 3, …​.
  * `DELIVERY_TIME` must be in `yyyy-mm-dd hhmm` format and must be a future date/time.
  * If `DELIVERY_ADDRESS` is not provided, the customer's stored address will be used.
  * If `STATUS` is not provided, it defaults to `PREPARING`. Valid statuses: `PREPARING`, `READY`, `DELIVERED`, `CANCELLED`.
* After a successful edit, the full order list is shown again.

**Examples:**
* `edit-o 2 q/5` — changes the quantity of the 2nd order in the list to `5`.
* `edit-o 1 s/READY` — marks the first pizza order in the search results as ready.
* `edit-o 1 i/Salad at/2026-05-01 1800 a/Blk 123 Main Street` — updates item, delivery time, and address for the first order in the current list.

</div>

<div class="section-spacing">

### <a id="list-o"></a>Listing all orders : `list-o`

Shows a list of all orders in the order database.

Format: `list-o`

</div>

### <a id="delete-o"></a>Deleting an order : `delete-o`

Deletes the specific order from the order database.

Format: `delete-o ORDER_INDEX`

* Deletes the order at the specified `ORDER_INDEX`.
* The order index refers to the index number shown in the displayed order list.
* The index **must be a positive integer** 1, 2, 3, …​

**Examples:**
* `list-o` followed by `delete-o 3` deletes the 3rd order in the results of the `list-o` command.
* `find-o i/pizza` followed by `delete-o 1` deletes the 1st order in the results of the `find-o` command.

---

## <a id="other-commands"></a>Other Commands

<div class="section-spacing">

### <a id="clear"></a>Clearing all entries : `clear`

Clears all customers and their orders from BZNUS.

Format: `clear`

</div>

<div class="section-spacing">

### <a id="exit"></a>Exiting the program : `exit`

Exits the program.

Format: `exit`

</div>

---
## <a id="data-storage"></a>Data Storage

<div class="section-spacing">

### <a id="saving-data"></a>Saving the data

BZNUS data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

</div>

<div class="section-spacing">

### <a id="edit-data"></a>Editing the data file

BZNUS data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, BZNUS will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause BZNUS to behave in unexpected ways (e.g. if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

</box>

</div>

### <a id="archive-data"></a>Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## <a id="faq"></a>FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**:
1. Install BZNUS on the new computer, run it once, then close the app.
2. On your old computer, open the folder containing `bznus.jar`, then go to `data/addressbook.json`.
3. Copy `addressbook.json` to the same location on the new computer (`[JAR file location]/data/`) and replace the existing data file.
4. Start BZNUS again. Your customers and orders should appear.

--------------------------------------------------------------------------------------------------------------------

## <a id="known-issues"></a>Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## <a id="command-summary"></a>Command summary

<div class="section-spacing">

### <a id="c-command"></a>Customer Commands

| Action              | Format, Examples                                                                                                                                                                                                                  |
|---------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Customer**    | `add n/NAME [p/PHONE_NUMBER] [fb/FACEBOOK] [ig/INSTAGRAM] [a/ADDRESS] [r/REMARK] [t/TAG]…​` <br> e.g., `add n/James Ho p/99996666 fb/james.Ho ig/james_Ho a/123, Clementi Rd, 1234665 r/extra spicy, no onion t/friend t/regular` |
| **Edit Customer**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [fb/FACEBOOK] [ig/INSTAGRAM] [a/ADDRESS] [r/REMARK] [t/TAG]…​` <br> e.g., `edit 2 n/James Lee ig/jamesLee`                                                                                  |
| **Find Customer**   | `find KEYWORD [MORE_KEYWORDS]` <br> e.g., `find James Jake` <br> Or `find PREFIX/KEYWORD` <br> e.g., `find fb/james` or `find ig/james_ho`                                                                                        |
| **List Customers**  | `list`                                                                                                                                                                                                                            |
| **Delete Customer** | `delete INDEX` <br> e.g., `delete 3`                                                                                                                                                                                              |

</div>

<div class="section-spacing">

### <a id="o-command"></a>Order Commands

| Action           | Format, Examples                                                                                                                                                                      |
|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Order**    | `order INDEX i/ITEM_NAME q/QUANTITY at/DELIVERY_TIME [a/DELIVERY_ADDRESS] [s/STATUS]` <br> e.g., `order 3 i/Pizza q/3 at/2026-04-02 1200 a/123 Jurong West St 42, #05-01 s/PREPARING` |
| **Find Order**   | `find-o Category-Type/Category-Keywords` <br> e.g., `find-o i/pizza`                                                                                                                  |
| **Edit Order**   | `edit-o ORDER_INDEX [i/ITEM_NAME] [q/QUANTITY] [at/DATE] [a/DELIVERY_ADDRESS] [s/STATUS]` <br> e.g., `edit-o 2 q/5 s/READY`                                                           |
| **List Orders**  | `list-o`                                                                                                                                                                              |
| **Delete Order** | `delete-o ORDER_INDEX` <br> e.g., `delete-o 1`                                                                                                                                        |

</div>

### <a id="others"></a>Other Commands

| Action    | Format, Examples |
|-----------|------------------|
| **Help**  | `help`           |
| **Clear** | `clear`          |
| **Exit**  | `exit`           |
