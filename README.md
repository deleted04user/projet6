# Contact Manager

Contact Manager is a desktop project for storing personal and professional contacts locally.

## Run Contact Manager

Use JDK 17 and Maven 3.9. Run these commands from the directory containing `pom.xml`.

```sh
mvn clean compile
mvn javafx:run
```

The app creates `contacts.db` in the working directory and opens the Contact Manager window. The form actions and table data will be connected in Phase 3. Search and group filtering will be connected in Phase 4.

The database contains one table, `contacts`. SQLite generates an ID for each contact. Name, phone, and group are required by the table. Email is optional. Phone numbers are stored as text so leading zeros are preserved.
