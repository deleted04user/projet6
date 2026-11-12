package com.contactmanager;

import java.sql.SQLException;

public class ContactManagerApp {
    public static void main(String[] args) {
        try {
            Database.initialize();
            System.out.println("Contact Manager database is ready.");
        } catch (SQLException exception) {
            System.err.println("Could not initialize contacts.db: " + exception.getMessage());
            System.exit(1);
        }
    }
}
