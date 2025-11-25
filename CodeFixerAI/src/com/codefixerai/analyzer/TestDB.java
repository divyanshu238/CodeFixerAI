package com.codefixerai.main;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Simple diagnostic utility for testing the database connection and queries.
 *
 * Used while developing or debugging to verify that:
 * - The database can be reached.
 * - Basic queries execute successfully.
 */

public class TestDB {

    /**
     * Standalone test runner for database connectivity.
     *
     * Executes a small query and prints the result to the console
     * so you can confirm that the DB configuration is working correctly.
     */

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/codefixerai",
                    "root",
                    "codefixer@123"
            );

            System.out.println("✅ Connected successfully!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
