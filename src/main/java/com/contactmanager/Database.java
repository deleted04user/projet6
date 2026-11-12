package com.contactmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:sqlite:contacts.db";

    static Connection connect() throws SQLException {
        // SQLite creates the file when the first connection opens.
        return DriverManager.getConnection(URL);
    }

    public static void initialize() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS contacts (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    phone TEXT NOT NULL,
                    email TEXT,
                    group_name TEXT NOT NULL
                )
                """;

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }
}
