package com.codefixerai.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Central place for managing database connections.
 *
 * Usually wraps the logic for:
 * - Creating JDBC connections.
 * - Reusing or closing connections safely.
 */

public class DBConnectionManager {

    private static final String URL = "jdbc:mysql://localhost:3306/codefixerai";
    private static final String USER = "root";
    private static final String PASSWORD = "YOUR_PASSWORD"; //// TODO: Reviewer - Please update this password to match your local MySQL configuration


    /**
     * Creates or retrieves a JDBC Connection to the configured database.
     *
     * @return An open Connection instance ready for use with SQL statements.
     */

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
