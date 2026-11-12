# Contact Manager

Contact Manager is a desktop project for storing personal and professional contacts locally.

## Current phase

**Phase 2 is complete.**

## What’s done

- **Phase 1 — Project foundation.** Set up Maven, JavaFX, the contact model, and the SQLite database. The data layer can add, list, update, and delete contacts.
- **Phase 2 — Desktop interface.** Built and styled the contact form, action buttons, contacts table, contact count, search field, and group filter. The table opens empty and the form actions are disabled until Phase 3.

## What’s next

- **Phase 3 — Contact management.** Load saved contacts into the table, connect the add, update, delete, and clear buttons, and refresh the table and contact count after changes.
- **Phase 4 — Search and filtering.** Connect search by name or phone number and enable filtering by group.

## Run Contact Manager

Use JDK 17 and Maven 3.9. Run these commands from the directory containing `pom.xml`.

```sh
mvn clean compile
mvn javafx:run
```

The app creates `contacts.db` in the working directory and opens the Contact Manager window.

The database contains one table, `contacts`. SQLite generates an ID for each contact. Name, phone, and group are required by the table. Email is optional. Phone numbers are stored as text so leading zeros are preserved.
