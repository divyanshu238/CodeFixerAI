package com.codefixerai.ui;

import com.codefixerai.db.AnalysisRecordDAO;
import com.codefixerai.model.AnalysisRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Separate window that shows previous analysis runs from the database.
 *
 * It typically:
 * - Fetches records via AnalysisRecordDAO.
 * - Displays them in a JTable with timestamp and issue count.
 */


public class HistoryFrame extends JFrame {

    private final JTable table;
    private final DefaultTableModel model;

    /**
     * Creates and lays out the history table UI.
     *
     * Configures:
     * - Table model columns.
     * - Scroll panes and sizing.
     * - Initial loading of analysis history from the database.
     */

    public HistoryFrame() {
        setTitle("Analysis History");
        setSize(600, 350);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new Object[]{"Analyzed At", "Issue Count"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        loadData();

        setVisible(true);
    }

    /**
     * Loads all analysis records from the database and populates the table model.
     *
     * Called from the constructor or when the user refreshes the history.
     */

    private void loadData() {
        model.setRowCount(0);
        AnalysisRecordDAO dao = new AnalysisRecordDAO();
        List<AnalysisRecord> records = dao.getAll();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (AnalysisRecord r : records) {
            model.addRow(new Object[]{
                    r.getAnalyzedAt().format(fmt),
                    r.getIssueCount()
            });
        }
    }
}
