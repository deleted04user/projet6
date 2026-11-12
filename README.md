# Contact Manager

Contact Manager is a desktop project for storing personal and professional contacts locally.

## Run the Phase 1 foundation

Use JDK 17 and Maven 3.9. Run these commands from the directory containing `pom.xml`.

```sh
mvn clean compile
mvn javafx:run
```

The current entry point creates `contacts.db` in the working directory and prints a confirmation. The JavaFX interface is planned for Phase 2.

The database contains one table, `contacts`. SQLite generates an ID for each contact. Name, phone, and group are required by the table. Email is optional. Phone numbers are stored as text so leading zeros are preserved.
