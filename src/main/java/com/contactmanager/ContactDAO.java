package com.contactmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    public void addContact(Contact contact) throws SQLException {
        String sql = "INSERT INTO contacts (name, phone, email, group_name) VALUES (?, ?, ?, ?)";

        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getPhone());
            statement.setString(3, contact.getEmail());
            statement.setString(4, contact.getGroupName());
            statement.executeUpdate();
        }
    }

    public List<Contact> getAllContacts() throws SQLException {
        String sql = "SELECT id, name, phone, email, group_name FROM contacts ORDER BY id";
        List<Contact> contacts = new ArrayList<>();

        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rows = statement.executeQuery()) {
            while (rows.next()) {
                contacts.add(new Contact(
                        rows.getInt("id"),
                        rows.getString("name"),
                        rows.getString("phone"),
                        rows.getString("email"),
                        rows.getString("group_name")
                ));
            }
        }
        return contacts;
    }

    public boolean updateContact(Contact contact) throws SQLException {
        String sql = "UPDATE contacts SET name = ?, phone = ?, email = ?, group_name = ? WHERE id = ?";

        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getPhone());
            statement.setString(3, contact.getEmail());
            statement.setString(4, contact.getGroupName());
            statement.setInt(5, contact.getId());
            // A false result tells the caller that the contact no longer exists.
            return statement.executeUpdate() == 1;
        }
    }

    public boolean deleteContact(int id) throws SQLException {
        String sql = "DELETE FROM contacts WHERE id = ?";

        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        }
    }
}
