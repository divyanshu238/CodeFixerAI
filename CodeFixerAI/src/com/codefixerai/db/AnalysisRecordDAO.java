package com.codefixerai.db;

import com.codefixerai.model.AnalysisRecord;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class used for performing database operations
 * related to storing and retrieving analysis history records.
 *
 * Responsibilities:
 * - Provide methods that interact with the database layer
 * - Insert new analysis results
 * - Retrieve previously stored history entries
 *
 * This class ensures that the rest of the application does not need to
 * handle SQL logic directly, promoting clean separation of concerns.
 */
public class AnalysisRecordDAO {

    /**
     * Saves a new analysis record into the database using JDBC.
     *
     * Steps performed:
     * 1. Establish connection using DBConnectionManager
     * 2. Prepare SQL INSERT query
     * 3. Bind values (issue count and timestamp)
     * 4. Execute update to persist data
     *
     * @param record The AnalysisRecord object containing issue count and analysis time
     */
    public void save(AnalysisRecord record) {
        String sql = "INSERT INTO analysis_history(issue_count, analyzed_at) VALUES (?, ?)";

        // try-with-resources ensures PreparedStatement and Connection close automatically
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Store issue count
            ps.setInt(1, record.getIssueCount());

            // Convert LocalDateTime to SQL Timestamp
            ps.setTimestamp(2, Timestamp.valueOf(record.getAnalyzedAt()));

            // Execute INSERT command
            ps.executeUpdate();

        } catch (SQLException e) {
            // Print stack trace if database error occurs
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all previously saved analysis records from the database.
     *
     * Steps performed:
     * 1. Establish database connection
     * 2. Execute SELECT query to fetch history records
     * 3. Convert ResultSet rows to AnalysisRecord objects
     * 4. Store them in a List and return
     *
     * @return List of AnalysisRecord objects ordered from newest to oldest
     */
    public List<AnalysisRecord> getAll() {
        List<AnalysisRecord> list = new ArrayList<>();
        String sql = "SELECT issue_count, analyzed_at FROM analysis_history ORDER BY analyzed_at DESC";

        // try-with-resources ensures ResultSet, PreparedStatement, and Connection are closed
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Loop through all rows returned
            while (rs.next()) {

                // Read issue count column
                int count = rs.getInt("issue_count");

                // Convert SQL Timestamp to LocalDateTime
                Timestamp ts = rs.getTimestamp("analyzed_at");
                LocalDateTime time = ts.toLocalDateTime();

                // Create model object and add to list
                list.add(new AnalysisRecord(count, time));
            }

        } catch (SQLException e) {
            // Print error if query fails
            e.printStackTrace();
        }

        // Return list of history records
        return list;
    }
}
