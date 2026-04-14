---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# BZNUS User Guide

BZNUS is a **one-stop desktop app for managing customer contacts, food orders and personalised customer preferences.** It provides a fast, reliable way for **home‑based food and beverage (F&B) business owners** to organise customer information and track orders in one place. Designed with a Command Line Interface (CLI) for speed and supported by a clean Graphical User Interface (GUI), BZNUS helps you complete customer‑management and order-tracking tasks more efficiently than traditional GUI‑only apps.

--------------------------------------------------------------------------------------------------------------------

## Target Users and Assumptions

**BZNUS is built for small home-based F&B business owners who:**
- handle customer orders, order fulfilment, and personalised preferences across multiple platforms such as WhatsApp, Instagram, and Facebook
- want to consolidate scattered customer information into one organised system
- prefer fast, keyboard‑driven workflows and are comfortable with basic CLI usage
- anticipate growing their customer base and need a system that scales with their business
- understand their business needs and can manage customer contact details responsibly

**Assumptions about users:**
- You have basic familiarity with CLI usage.
- You understand your business operations and can maintain accurate customer information.

--------------------------------------------------------------------------------------------------------------------

## <a id="quick-start"></a>Quick Start

1. Ensure you have Java `17` or above installed on your computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-W09-3/tp/releases).

3. Copy the `.jar` file to the folder you want to use as the _home folder_ for BZNUS.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar bznus.jar` command to run the application.<br>
   A GUI like this should appear in a few seconds. Note how the app contains some sample data.<br>\
   ![Ui](images/Ui.png)<br>\
On startup, the order list is automatically filtered to display only active orders (orders with the statuses `PREPARING` or `READY`) to reduce clutter.

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all customers.

   * `add n/John Doe p/98765432 a/John street, block 123, #01-01` : Adds a customer named `John Doe` to the customer database.

   * `order 1  i/Pizza  q/3  at/2026-06-02 1200 a/123 Jurong West St 42, #05-01 s/PREPARING` : Adds an order for 3 pizzas to the 1st customer in the current list.

   * `delete 3` : Deletes the 3rd customer shown in the current list.

   * `clear` : Shows a confirmation message before deleting all customers and their orders.

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

* Items with `…`​ after them can be used multiple times, including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family`, etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list` and `exit`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines. Space characters surrounding line-breaks may be omitted when copied over to the application.
  * **Solution:** Type the command manually if pasting doesn't work.

</box>

### <a id="viewing-help"></a>Viewing Help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)
* The keyword `help` is case-insensitive

Format: `help`

--------------------------------------------------------------------------------------------------------------------

## <a id="customer-commands"></a>Customer Commands

<div class="section-spacing">

### <a id="add"></a>Adding a Customer : `add`

Adds a customer to the customer database.

Format: `add n/NAME [p/PHONE] [ig/INSTAGRAM] [fb/FACEBOOK] [a/ADDRESS] [r/REMARK] [t/TAG]…​`

* `NAME` is mandatory. It must be 1 to 100 characters long, start with an alphanumeric character, and contain only letters, numbers, spaces, apostrophes (`'`), slashes (`/`), and hyphens (`-`).
* If you need a `/` in the name, type **`\/`** for a literal slash. BZNUS stores the name with a normal `/` (the backslash is not kept). Example: `n/Ravi S\/O Ramasamy` is stored as `Ravi S/O Ramasamy`.
* `PHONE` must be 7 to 15 digits long and contain only numbers (e.g. 9123456 or 60123456789). No spaces, '+' sign, or other symbols are allowed.
* `INSTAGRAM` must be 1 to 30 characters long and contain only letters, numbers, underscores, and periods. It must not end with a period or have consecutive periods. No internal whitespaces allowed. The `@` prefix is optional.
* `FACEBOOK` must be 5 to 50 characters long and contain only letters, numbers, and periods. It must not have leading, trailing, or consecutive periods. No internal whitespaces allowed. The `@` prefix is optional.
* `ADDRESS` must only contain alphanumeric characters, spaces, and the following special characters: ,.'/#&()-. It must start with an alphanumeric character, and cannot exceed 200 characters.
* `REMARK` can be any non-blank string, but cannot exceed 500 characters.
* `TAG` must contain at least one letter or number, and may include spaces, underscores, and hyphens. 
  * A customer can have any number of tags (including 0).
  * To specify multiple tags, repeat the `t/` prefix.  
      For example: `t/friend t/vegan t/regular`.  
      All tags provided will be added to the new customer.
* **Stray prefixes:** In `n/`, `p/`, `fb/`, `ig/`, and `t/`, an invalid prefix like `x/` is rejected. **`r/` and `a/`** skip this check so `/` in remarks and addresses stays literal.

<box type="important" seamless>

**Note:** A customer must have **at least one** contact method: `p/PHONE`, `ig/INSTAGRAM`, or `fb/FACEBOOK`. The command will fail and show an error message if all contact methods are missing.

</box>

<box type="important" seamless>

**Duplicate Handling:** Customer names are unique (**case-insensitive**). Extra spaces in names are cleaned up (leading/trailing spaces removed, internal spaces collapsed into one). For example, "John Doe", "john doe", and "   John      Doe" are considered the same person, and the app will reject the duplicate entry.

</box>

<box type="info" seamless>

**Note**: BZNUS allows multiple customers to share the **same phone number,
Instagram handle, or Facebook username**. This is **intentional**, as shared
contact methods are common in real-world scenarios (e.g. corporate accounts, household landlines, or shared business social media).

However, if you add a customer with contact details that overlap with an existing customer, BZNUS will show a **non-blocking warning** in the result message. You can review the warning and use `find` to check for potential duplicates.

</box>

<box type="tip" seamless>

**Tip:** If you have two customers with the same name, use descriptors to differentiate them (e.g. "**John Doe (Clementi)**" and "**John Doe (Jurong)**").

</box>

Examples:
1. `add n/John Doe p/98765432 ig/john a/John Street, Blk 123, #01-01 r/prefers weekend delivery t/halal t/regular`
2. `add n/Betsy Crowe t/friend fb/betsy.crowe a/Blk 456, Bedok North r/allergic to peanuts`
3. `add n/Tech Corp SG p/67778888 ig/techcorp.sg a/Tech Tower, Level 12 r/Invoicing required`

<box type="info" seamless>

**Expected output:**
On success, if all fields are provided, you will see the following message:
```
New customer added: NAME
Phone: PHONE | Facebook: FACEBOOK | Instagram: INSTAGRAM
Address: ADDRESS
Remark: REMARK
Tags: TAG1, TAG2, ...
```
Note that only the fields provided in the command will be shown in the output. For example, if you add a customer with only name and phone number, the output will only show the name, phone number and `Tags: -`.

**Sample output for Example 1:**
![Sample output for adding a customer](images/addCustomerSampleOutput.png)
<br>

**Sample output (top of message): non-blocking duplicate contact warning:**<br>
Duplicate fields detected: `INSTAGRAM: alex.yeoh`, `FACEBOOK: Alex.Yeoh`
![Sample non-blocking duplicate contact warning](images/addCustomerDuplicateWarningSampleOutput.png)

Note: The command still succeeds; the added customer's details appear after the warning in the same result box.

If the customer name is a duplicate or invalid input is provided, an error message will be shown. Please refer to the [Troubleshooting](#troubleshooting) section for more details.

</box>

</div>

<div class="section-spacing">

### <a id="list"></a>Listing All Customers : `list`

Shows a list of all customers in the customer database.

Format: `list`

<box type="info" seamless>

**Expected output:**
Displays all customers in the database, or shows "Customer list is empty." if the database is empty.

![Sample output for listing customers](images/listCustomersSampleOutput.png)

</box>

<box type="tip" seamless>

**Tip:** This is a useful command to reset filters on the customer list at any time.

</box>

<box type="tip" seamless>

**Tag display:** Very long tags may be truncated in the customer card for readability.
Hover over a truncated tag to view its full text in a tooltip.

</box>

</div>

<div class="section-spacing">

### <a id="edit"></a>Editing a Customer : `edit`

Edits an existing customer in the displayed customer list.

Format: `edit INDEX [n/NAME] [p/PHONE] [ig/INSTAGRAM] [fb/FACEBOOK] [a/ADDRESS] [r/REMARK] [t/TAG]…​`

* Edits the customer at the specified `INDEX`. The index refers to the index number shown in the displayed customer list. The index **must be a positive integer** 1, 2, 3, …​
* At least one field to edit must be provided.
* Existing values will be replaced by the values you provide.
* You can clear the following single-valued fields by providing the prefix without a value:
  * `p/` clears phone
  * `ig/` clears Instagram
  * `fb/` clears Facebook
  * `a/` clears the address
  * `r/` clears remark
* `n/` (name) cannot be empty if present. Use `n/NEW_NAME` to change the name. The same **`\/`** rule as for [`add`](#add) applies if you need a literal `/` in the new name.
* After the edit is applied, the customer must still have at least one contact method (`p/`, `ig/`, or `fb/`). Otherwise, the edit is rejected.
* If the edited customer shares the same `PHONE`, `FACEBOOK`, or `INSTAGRAM` as an existing customer, BZNUS shows a non-blocking warning in the result message. The edit is still applied.
* Tags are handled as a set:
  * `t/TAG [t/MORE_TAGS]...` replaces all the customer's existing tags with the tag(s) provided. i.e., the addition of tags is not cumulative.
  * `t/` clears all existing tags.

<box type="warning" seamless>

**Important:** Updating a customer's address **does not** update the delivery address of their existing orders.

When an order is created **without specifying a delivery address**, the order takes a *snapshot* of the customer’s address at that moment. This address does **not stay linked** to the customer.

- Editing the customer’s address later **will not** change created orders.
- To update an order’s delivery address, use `edit-o ORDER_INDEX a/NEW_ADDRESS`.

This behaviour ensures that historical orders remain accurate even if the customer moves or changes their address.

</box>

Examples:
1. `edit 1 p/91234567 a/John Street, Blk 123, #02-02` Edits the phone number and delivery address of the 1st customer to be `91234567` and `John Street, Blk 123, #02-02` respectively.
2. `edit 2 n/Betsy Crower t/` Edits the name of the 2nd customer to be `Betsy Crower` and clears all existing tags.
3. `edit 3 ig/ r/` Clears Instagram and remark for the 3rd customer.
4. `edit 4 p/ fb/ ig/ a/` Fails as this would remove all contact methods from the 4th customer.

<box type="info" seamless>

**Expected output:**
On success, if the edited customer has valid values for all fields, you will see the following message:
```
Edited Customer: NAME
Phone: PHONE | Facebook: FACEBOOK | Instagram: INSTAGRAM
Address: ADDRESS
Remark: REMARK
Tags: TAG1, TAG2, ...
```

**Sample output for Example 1:**

**Before `edit`**  
<div style="margin-left: 30px; margin-bottom: 15px;">
  <img src="images/editCustomerBeforeSampleOutput.png" alt="Original customer before edit">
</div>

**After `edit`**
![Sample output for editing a customer](images/editCustomerSampleOutput.png)

If the command fails (e.g. invalid index, duplicate name, invalid command format, or the edited customer ends up with no contact methods), an error message will be shown. Please refer to the [Troubleshooting](#troubleshooting) section for more details.

Note: After you edit a customer, the order card may not update to reflect the changes in the customer's name or contact details unless you click the edited customer entry in the customer list again.

</box>

</div>

<div class="section-spacing">

### <a id="find"></a>Finding a Customer : `find`

Finds customers whose details match the given keywords. You can search across all fields or target a specific field using prefixes.

#### General Search
Format: `find KEYWORD`

* The search is case-insensitive. e.g. `hans` will match `Hans`.
* All fields are searched.
* Partial matches are supported, e.g. `Han` will match `Hans`.

Examples:
* `find John` returns `John` and `John doe`
  ![result for 'find John'](images/findJohn.png)
* `find 99272758` returns `Bernice Yu` if her contact details contain these digits<br>\
  ![result for 'find 99272758'](images/findBernice2.png)

<box type="important" seamless>

**Note:** All text after `find` is treated as a single keyword. For example, `find John Doe` searches for the phrase “John Doe” as one keyword, not two separate keywords.

</box>

#### Specific Field Search
Format: `find PREFIX/KEYWORD`

* The search is case-insensitive. e.g. `hans` will match `Hans`.
* For `fb/`, the search is period-insensitive. e.g. `find fb/johndoe` can match `john.doe`.
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

Note: 
The find command uses AND logic when multiple prefixes are used. In the above example, only customers who satisfy both conditions (name contains `Bob` AND remark contains `non-spicy`) will be returned.

</div>

### <a id="delete"></a>Deleting a Customer : `delete`

Deletes the specified customer from the customer database.

Format: `delete INDEX`

* Deletes the customer at the specified `INDEX`.
* The index refers to the index number shown in the displayed customer list.
* The index **must be a positive integer** 1, 2, 3, …​

<box type="important" seamless>

**Note:** All orders associated with the deleted customer will also be deleted.

</box>

Examples:
1. `list` followed by `delete 2` deletes the 2nd customer in the customer database.
2. `find Betsy` followed by `delete 1` deletes the 1st customer in the results of the `find` command.

<box type="info" seamless>

**Expected output:**
On success, you will see the following message:
```
Deleted Customer: NAME
Phone: PHONE | Facebook: FACEBOOK | Instagram: INSTAGRAM
Address: ADDRESS
Remark: REMARK
Tags: TAG1, TAG2, ...
```
Note that only the fields that exist for the deleted customer will be shown in the output.

**Sample output:**
![Sample output for Delete Customer](images/deleteCustomerSampleOutput.png)

</box>

---

## <a id="order-commands"></a>Order Commands

<div class="section-spacing">

### <a id="order"></a>Adding an Order : `order`

Adds a new order for a specific customer.

Format: `order INDEX i/ITEM_NAME q/QUANTITY at/DELIVERY_TIME [a/DELIVERY_ADDRESS] [s/STATUS]`

* Adds an order to the customer at the specified `INDEX`.
* The index refers to the index number shown in the displayed customer list.
* The index **must be a positive integer** 1, 2, 3, …​
* `ITEM_NAME` must **begin with a letter or a number**, contain only alphanumeric characters, spaces, and basic punctuation (e.g. '-', '&', apostrophes), and **cannot be blank**.
* `QUANTITY` **must be a positive integer without formatting characters** such as commas and spaces.\
  If you enter an unusually large quantity (200 or more), the order will still be added, but a warning will be shown to help prevent accidental input errors.
* `DELIVERY_TIME` must be in `yyyy-mm-dd hhmm` format.\
If the time entered is not in the future, the order will still be added (to support recording of completed orders), but a warning will be shown.
* If `DELIVERY_ADDRESS` is not provided, the customer's stored address will be used. Later changes to the customer’s address **will not** affect the delivery addresses of orders that have already been created.\
If the customer has no stored address, the system displays an error message prompting you to enter a delivery address for the order.
* If `STATUS` is not provided, it defaults to `PREPARING`. Valid statuses: `PREPARING`, `READY`, `DELIVERED`, `CANCELLED`.

<box type="important" seamless>

**Duplicate orders allowed:** You can add multiple orders with identical details, as customers may place the same order more than once. You may also wish to split a large batch order into multiple smaller orders for easier status tracking (since each order entry can only be assigned a single status).

</box>

<box type="tip" seamless>

**Tip:** You can specify a/PICKUP for pickup orders.

</box>

**Examples:**
1. `order 1 i/Pizza q/3 at/2026-06-02 1200`
2. `order 2 i/Burger q/5 at/2026-07-15 1800 a/123 Jurong West St 42, #05-01`
3. `order 3 i/Salad q/2 at/2026-08-10 1200 s/DELIVERED`

<box type="info" seamless>

**Expected output:**
On success, if all fields are provided, you will see the following message:
```
New order added: ITEM (xQUANTITY) for CUSTOMER_NAME.
Delivery to: ADDRESS
At: DELIVERY_TIME | Status: STATUS
```

**Sample output for Example 1:**
![Sample output for Add Order](images/addOrderSampleOutput.png)
<br>

</box>

</div>

<div class="section-spacing">

### <a id="find-o"></a>Finding an Order : `find-o`

Search for different orders with 4 category options: item name, delivery address, customer, and status

Format: `find-o Category-Type/Category-Keywords`

* Find the orders given the `Category-Keywords` from the `Category-Type`.
* The category keywords refer to the keywords used to look for orders.
* The category type refers to one of the 4 category options shown above.
* The category type **must be one of i/a/c/s**, which respectively stand for item, address, customer index, and status.
* Allows searching with multiple prefixes.

**Examples:**
* `find-o i/pizza` - Look for orders with the item keyword "pizza"
* `find-o a/Ang Mo Kio` - Look for orders with delivery address "Ang Mo Kio"
* `find-o s/Delivered` - Look for orders that are already delivered
* `find-o i/burger a/Ang Mo Kio` - Look for orders with item keyword "burger" and delivery address "Ang Mo Kio"
![result for 'find-o i/burger a/Ang Mo Kio'](images/find-o.png)


<box type="tip" seamless>

**Tip:** Besides using `c/`, you can also click on a customer entry in the list to view all orders of that customer.
![Click a customer entry to view all orders](images/customer-click-view-orders.png)

</box>

</div>

<div class="section-spacing">

### <a id="edit-o"></a>Editing an Order : `edit-o`

Updates the fields of an existing order. Any field you specify replaces the previous value; other fields stay unchanged.

Format: `edit-o ORDER_INDEX [i/ITEM_NAME] [q/QUANTITY] [at/DELIVERY_TIME] [a/DELIVERY_ADDRESS] [s/STATUS]`

* Edits the order at the specified `ORDER_INDEX`. The index refers to the order number shown in the **currently displayed order list**. The index **must be a positive integer**.
* **At least one** of `i/`, `q/`, `at/`, `a/`, or `s/` must be provided. Omitting all of them is not allowed.
* The order **stays with the same customer**; you cannot reassign an order to another customer with this command.
* Field rules are the same as when using [`order`](#order) (see [Adding an Order](#order)):
  * `ITEM_NAME` must **begin with a letter or a number**, contain only alphanumeric characters, spaces, and basic punctuation (e.g. '-', '&', apostrophes), and **cannot be blank**.
  * `QUANTITY` **must be a positive integer without formatting characters** such as commas and spaces.
  * `DELIVERY_TIME` must be in `yyyy-mm-dd hhmm` format.
    If you supply `at/` and the time is not in the future, a warning is shown (same behaviour as **`order`**), but the edit still applies. This is useful for recording completed orders.
  * If `DELIVERY_ADDRESS` is omitted in `edit-o`, the order **keeps its current delivery address**. (This differs from **`order`**, where omitting `a/` fills in the customer's stored address when present.)
  * If `STATUS` is not provided, the order keeps its current status.
* After a successful edit, the displayed order list updates to reflect the change.

**Examples:**
* `edit-o 2 q/5` — changes the quantity of the 2nd order in the list to `5`.
* `edit-o 1 s/READY` — marks the first pizza order in the search results as ready.
* `edit-o 1 i/Salad at/2026-05-01 1800 a/Blk 123 Main Street` — updates item, delivery time, and address for the first order in the current list.

<box type="info" seamless>

**Sample Output for Example 3:**

**Before `edit-o`**
![Order list before edit-o](images/edit-o-before.png)

**After `edit-o`**
![Order list after edit-o](images/edit-o-after.png)

</box>

</div>

<div class="section-spacing">

### <a id="list-o"></a>Listing All Orders : `list-o`

Shows a list of all orders in the order database.

Format: `list-o`

<box type="info" seamless>

**Expected output:**
Displays all orders in the database, or shows "Order list is empty." if the database is empty.

![Sample output for Orders](images/listOrdersSampleOutput.png)

</box>

<box type="tip" seamless>

**Tip:** This is a useful command to reset filters on the order list at any time.

</box>

</div>

### <a id="delete-o"></a>Deleting an Order : `delete-o`

Deletes the specific order from the order database.

Format: `delete-o ORDER_INDEX`

* Deletes the order at the specified `ORDER_INDEX`.
* The order index refers to the index number shown in the displayed order list.
* The index **must be a positive integer** 1, 2, 3, …​

**Examples:**
1. `list-o` followed by `delete-o 3` deletes the 3rd order in the results of the `list-o` command.
2. `find-o i/pizza` followed by `delete-o 1` deletes the 1st order in the results of the `find-o` command.

<box type="info" seamless>

**Expected output:**
On success, you will see the following message:
```
Deleted order: ITEM (xQUANTITY) for CUSTOMER_NAME.
Delivery to: ADDRESS
At: DELIVERY_TIME | Status: STATUS
```

**Sample output:**
![Sample output for Delete Order](images/deleteOrderSampleOutput.png)
<br>

</box>

---

## <a id="other-commands"></a>Other Commands

<div class="section-spacing">

### <a id="clear"></a>Clearing All Entries : `clear`

Clears all customers and their orders from BZNUS.
To prevent accidental data loss, this command requires a specific confirmation keyword to execute.

Format:
* `clear` (shows confirmation message)
  ![clear command warning](images/clearCommandDisplayWarning.png)
* `clear CONFIRM` (confirms and permanently deletes all data)
  ![clear confirm result](images/clearConfirm.png)

**Note:** `clear` only shows a warning. `clear CONFIRM` executes immediately, even if `clear` was not run just before.

<box type="important" seamless>

**Note:** This action is irreversible. Once you run a clear CONFIRM, all customer profiles, order histories, and related data will be permanently removed from the application.

</box>

</div>

<div class="section-spacing">

### <a id="exit"></a>Exiting the Program : `exit`

Exits the program.

Format: `exit`

</div>

---
## <a id="data-storage"></a>Data Storage

<div class="section-spacing">

### <a id="saving-data"></a>Saving the Data

BZNUS data is saved to disk automatically whenever a command **completes successfully** (failed commands do not write to the file). There is no need to save manually.

</div>

<div class="section-spacing">

### <a id="edit-data"></a>Editing the Data File

BZNUS data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to edit this file directly, but please read the following notes carefully before doing so.

<box type="important" seamless>

**Important:** Only edit `addressbook.json` when BZNUS is **not running**.<br>
If you modify the file while the app is open, your changes will not be loaded into the current session. When you next run a command that saves data (e.g. `exit`), the app will overwrite the file and discard your manual changes.

</box>

<box type="warning" seamless>

**Caution:**
Certain edits (e.g. entering out-of-range values) can cause BZNUS to behave in unexpected ways. Only edit the data file if you are confident that you can update it correctly.<br>

</box>

<box type="warning" seamless>

**Caution: Corrupted Data File**
If your changes to the data file make its format invalid or violate app rules (e.g. a customer ends up with no contact methods), your data may not load correctly. It is recommended to make a backup of the file before editing it.

**Save behaviour:**
To help you understand how BZNUS handles a corrupted `addressbook.json`:

- If the data file is corrupted, BZNUS starts with empty customer and order lists, but it **does not overwrite the corrupted file** unless you execute a command that saves data.
- **Commands that save data:**
  - Any command that modifies the data (e.g. `add`, `edit`, `delete`, `clear`, `exit`) will save the current app state and **overwrite** the corrupted file.
- **Actions that do _not_ save data:**
  - Closing the window using the **`X`** button or pressing **`Ctrl+C`** in the terminal where BZNUS is running does **not** write anything to the data file. The corrupted file remains unchanged, so BZNUS will load empty lists again on the next startup as long as the file is still invalid.

</box>

<box type="tip" seamless>

**Tip:** To fix corrupted data without losing everything you've stored:
1. Close BZNUS first by clicking the **`X`** button.
2. Edit `addressbook.json` manually.
3. Reopen BZNUS.

</box>

</div>

--------------------------------------------------------------------------------------------------------------------

## <a id="faq"></a>FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**:
1. Install BZNUS on the new computer, run it once, then close the app.
2. On your old computer, open the folder containing `bznus.jar`, then go to `data/addressbook.json`.
3. Copy `addressbook.json` to the same location on the new computer (`[JAR file location]/data/`) and replace the existing data file.
4. Start BZNUS again. Your customers and orders should appear.

--------------------------------------------------------------------------------------------------------------------

## <a id="known-issues"></a>Known Issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimise the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimised, and no new Help Window will appear. The remedy is to manually restore the minimised Help Window.

--------------------------------------------------------------------------------------------------------------------

## <a id="command-summary"></a>Command Summary

<div class="section-spacing">

### <a id="c-command"></a>Customer Commands

| Action              | Format, Examples                                                                                                                                                                                                                  |
|---------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Customer**    | `add n/NAME [p/PHONE_NUMBER] [fb/FACEBOOK] [ig/INSTAGRAM] [a/ADDRESS] [r/REMARK] [t/TAG]…​` <br> e.g., `add n/James Ho p/99996666 fb/james.Ho ig/james_Ho a/123, Clementi Rd, 1234665 r/extra spicy, no onion t/friend t/regular` |
| **Edit Customer**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [fb/FACEBOOK] [ig/INSTAGRAM] [a/ADDRESS] [r/REMARK] [t/TAG]…​` <br> e.g., `edit 2 n/James Lee ig/jamesLee`                                                                                  |
| **Find Customer**   | `find KEYWORD` <br> e.g., `find James Ho` <br> Or `find PREFIX/KEYWORD` <br> e.g., `find fb/james` or `find ig/james_ho`                                                                                                          |
| **List Customers**  | `list`                                                                                                                                                                                                                            |
| **Delete Customer** | `delete INDEX` <br> e.g., `delete 3`                                                                                                                                                                                              |

</div>

<div class="section-spacing">

### <a id="o-command"></a>Order Commands

| Action           | Format, Examples                                                                                                                                                                      |
|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Order**    | `order INDEX i/ITEM_NAME q/QUANTITY at/DELIVERY_TIME [a/DELIVERY_ADDRESS] [s/STATUS]` <br> e.g., `order 3 i/Pizza q/3 at/2026-04-02 1200 a/123 Jurong West St 42, #05-01 s/PREPARING` |
| **Find Order**   | `find-o Category-Type/Category-Keywords` <br> e.g., `find-o i/pizza`                                                                                                                  |
| **Edit Order**   | `edit-o ORDER_INDEX [i/ITEM_NAME] [q/QUANTITY] [at/DELIVERY_TIME] [a/DELIVERY_ADDRESS] [s/STATUS]` <br> e.g., `edit-o 2 q/5 s/READY`                                                  |
| **List Orders**  | `list-o`                                                                                                                                                                              |
| **Delete Order** | `delete-o ORDER_INDEX` <br> e.g., `delete-o 1`                                                                                                                                        |

</div>

### <a id="others"></a>Other Commands

| Action    | Format, Examples |
|-----------|------------------|
| **Help**  | `help`           |
| **Clear** | `clear` <br>`clear CONFIRM` |
| **Exit**  | `exit`           |

--------------------------------------------------------------------------------------------------------------------

## Troubleshooting
This section provides quick fixes for common user-facing issues.

### Add Customer (`add`)

<panel header="Duplicate customer name" type="seamless">

**Error shown:**
"A customer with the same name already exists in the database."

**Why this happens:**  
Customer names are unique (case-insensitive). Extra spaces in names are cleaned up (leading/trailing spaces removed, internal spaces collapsed into one).

**What to do:**
Use a different name that is not already in the customer database
(e.g. include a descriptor such as `John Doe (Jurong)`).

Tip: Use **tags** or **remarks** to further differentiate customers with similar names.

</panel>

<a id="duplicate-contact-warning-troubleshooting"></a>

<panel id="duplicate-contact-warning" header="Duplicate contact details warning (non-blocking)" type="seamless">

**Warning shown:**
"WARNING: Duplicate contact details detected. This is allowed, but please verify..."

**Why this happens:**
The customer shares the same `PHONE`, `FACEBOOK`, or `INSTAGRAM` as an existing customer.

**What to do:**
- Review the matched fields in the warning.
- (Optional) Run the suggested `find` commands to review overlaps:
  - The warning may suggest `find p/PHONE`, `find fb/FACEBOOK`, or `find ig/INSTAGRAM` based on the matches found. `PHONE`, `FACEBOOK` and `INSTAGRAM` take on the new customer’s values.
  - Tip: You can also combine prefixes (e.g. `find p/PHONE ig/INSTAGRAM`) to check whether any customer matches multiple contact fields, which may indicate a likely duplicate.
- If the overlap is intentional (e.g. shared household/business contact), no action is needed.
- If it is unintentional, run `edit` to correct the contact field(s) of the added customer. Refer to [Editing a customer](#edit) for more details.

</panel>


<panel header="No contact method provided" type="seamless">

**Error shown:**
"At least one contact method (phone, Facebook, or Instagram) must be provided."

**Why this happens:**
At least one contact method is required.

**What to do:**
Include at least one of `p/`, `ig/`, or `fb/`.

</panel>

<a id="invalid-field-format-troubleshooting"></a>

<panel header="Invalid field format" type="seamless">

**Error shown:**
A field-specific error message (e.g. “Customer name must…”, “Phone number must…”, etc.).

**Why this happens:**
Each field has its own format rules. If the value you entered breaks that field’s rules, a field‑specific error appears.

**What to do:**
Identify the field that caused the error, correct its value, and try the command again. 
Refer to [Adding a customer](#add) for the full list of field requirements.

</panel>

<panel header="Invalid command format" type="seamless">

**Error shown:**
"Invalid command format! ..."

**Why this happens:**
The command is missing required prefixes or has the wrong structure.

**What to do:**
Ensure the `add` command includes `n/NAME` and at least one of `p/`, `ig/`, or `fb/`. 
Refer to [Adding a customer](#add) for the correct format.

</panel>

<a id="invalid-prefix-troubleshooting"></a>

<panel header="Unsupported/invalid prefix" type="seamless">

**Error shown (Example):**
"Unsupported prefix 'X/' in this command. Valid prefixes: n/, p/, fb/, ig/, a/, r/, t/."

**Why this happens:**  
A prefix not recognised by the command was used.

**What to do:**
* Remove or correct the unknown prefix. Use only the supported prefixes shown in the error message. 
* Refer to [Adding a customer](#add) or [Editing a customer](#edit) for the accepted command formats.
* If you intended to type a literal `/`, note that:
  * `/` is **not allowed** in phone, Facebook, Instagram, or tags. 
  * For names, escape it as `\/` (e.g. `n/John \/ Doe` → “John / Doe”). 
  * For address and remark, `/` can be typed normally.

</panel>

<panel header="Duplicate single-valued prefixes" type="seamless">

**Error shown (example):**
"Multiple values specified for the following single-valued field(s): n/"

**Why this happens:**
Prefixes like `n/`, `p/`, `fb/`, `ig/`, `a/`, and `r/` can only appear once.

**What to do:**
Keep to only one value for each single-valued prefix specified (`n/`, `p/`, `fb/`, `ig/`, `a/`, `r/`).

</panel>

<div style="height: 30px;"></div>

### Edit Customer (`edit`)

<panel header="Invalid index" type="seamless">

**Error shown:**
"The customer index provided is invalid. Please use an index from the displayed customer list."

**Why this happens:**
The index does not exist in the currently displayed customer list.

**What to do:**
1. Check that the customer you wish to edit is visible in the currently displayed list.
2. If needed, run `list` to show all customers again, or use `find KEYWORD` / `find PREFIX/KEYWORD` to narrow down the list.
3. Retry `edit` using an index that appears in that displayed list (e.g. `edit 1 ...` to edit the first customer shown).

</panel>

<panel header="Invalid field format" type="seamless">

Refer to [Invalid field format for `add`](#invalid-field-format-troubleshooting) for details.

</panel>

<panel header="Invalid command format" type="seamless">

**Error shown:**
"Invalid command format! ..."

**Why this happens:**
- Case 1: The customer index entered is not a positive integer.
- Case 2: The structure of the command is incorrect (e.g. you ran `edit INDEX` without specifying any fields to change).

**What to do:**
- Check the full error message shown in the app for the correct `edit` command format and an example.
- Ensure you provide:
  1. A positive integer index within the range of the currently displayed customer list; and
  2. At least one field to edit (e.g. `n/`, `a/`, `p/`, `fb/`, `ig/`, `r/`).
- Refer to [Editing a customer](#edit) for more details.

</panel>

<panel header="Unsupported/invalid prefix" type="seamless">

Refer to [Unsupported/invalid prefix for `add`](#invalid-prefix-troubleshooting) for details.

</panel>

<panel header="Edit rejected after clearing at least one of `p/`, `ig/`, or `fb/`" type="seamless">

**Error shown:**
"The edited customer must still have at least one contact method (phone, Facebook, or Instagram)."

**Why this happens:**
The edit would leave the customer with no contact methods.

**What to do:**
Ensure at least one of `p/`, `ig/`, or `fb/` remains after editing.

</panel>

<panel header="Duplicate customer after editing name" type="seamless">

**Error shown:**
"A customer with the same name already exists in the database."

**Why this happens:**  
Customer names are unique (case‑insensitive). Extra spaces in names are cleaned up (leading/trailing spaces removed, internal spaces collapsed into one).

**What to do:**
Use a different name that is not already in the customer database
(e.g. include a descriptor such as `John Doe (Jurong)`).

</panel>

<panel header="Duplicate contact details warning after editing" type="seamless">

Refer to [Duplicate contact details warning (non-blocking) for `add`](#duplicate-contact-warning-troubleshooting) for details.

</panel>

<panel header="Duplicate single‑valued prefixes" type="seamless">

**Error shown (example):**
"Multiple values specified for the following single-valued field(s): n/"

**Why this happens:**
Prefixes such as `n/`, `p/`, `fb/`, `ig/`, `a/`, and `r/` can only appear once in the command.

**What to do:**
Keep only one value for each single-valued prefix specified (`n/`, `p/`, `fb/`, `ig/`, `a/`, `r/`).

</panel>

<div style="height: 30px;"></div>

### List Customers (`list`)

<panel header="Customer list is empty after running `list`" type="seamless">

**Why this happens:**
There are currently no customers stored in BZNUS, or the data file (`addressbook.json`) could not be loaded.

**What to do:**
- Add a new customer using `add`, or
- If you expected existing data, check the [Editing the data file](#edit-data) section for steps to recover from a corrupted `addressbook.json`.

</panel>

<div style="height: 30px;"></div>
