package com.codefixerai.ui;

import com.codefixerai.analyzer.CodeAnalyzer;
import com.codefixerai.db.AnalysisRecordDAO;
import com.codefixerai.model.AnalysisRecord;
import com.codefixerai.model.Issue;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * MainFrame is the main GUI window of the CodeFixer AI application.
 * It provides:
 *  - A text area to enter/paste Java source code.
 *  - A text area to display analysis results.
 *  - Buttons to analyze code, view past analysis history, and export reports.
 *
 * This class also demonstrates:
 *  - OOP design (separation of UI, analyzer, and database logic)
 *  - Multithreading (background analysis thread)
 *  - Synchronization (for thread-safe access to analysis results)
 */
public class MainFrame extends JFrame {

    /**
     * Text area where the user types or pastes Java source code to be analyzed.
     */
    private final JTextArea codeArea;

    /**
     * Text area where analysis results (issues, summary) are displayed.
     */
    private final JTextArea outputArea;

    /**
     * Analyzer responsible for running all rule-based checks on the source code.
     */
    private final CodeAnalyzer analyzer;

    /**
     * Data Access Object (DAO) used to store and retrieve analysis history from the database.
     */
    private final AnalysisRecordDAO historyDao;

    /**
     * Lock object used to synchronize access to lastIssues between the
     * background analysis thread and the Export Report action.
     */
    private final Object issueLock = new Object();

    /**
     * Stores the issues from the most recent analysis.
     * This field is accessed by multiple threads (analysis thread and UI thread),
     * so all reads and writes must be synchronized using issueLock.
     */
    private List<Issue> lastIssues = new ArrayList<>();

    /**
     * Constructor sets up the main window, components, and layout.
     * It initializes the analyzer, DAO, and configures the dark-themed UI.
     */
    public MainFrame() {
        // Set basic window properties.
        setTitle("CodeFixer AI – Java Code Analyzer");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window on screen

        // Initialize core objects.
        analyzer = new CodeAnalyzer();
        historyDao = new AnalysisRecordDAO();

        // ----- CODE INPUT AREA (TOP) -----
        codeArea = new JTextArea();
        codeArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        codeArea.setLineWrap(false); // Keep code formatting

        // ----- OUTPUT / RESULT AREA (BOTTOM) -----
        outputArea = new JTextArea();
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        outputArea.setEditable(false);         // Read-only
        outputArea.setLineWrap(true);          // Wrap long lines
        outputArea.setWrapStyleWord(true);     // Wrap by words, not characters

        // ----- BUTTONS -----

        // Button to trigger code analysis in a background thread.
        JButton analyzeButton = new JButton("Analyze Code");
        analyzeButton.addActionListener(e -> analyzeCode());

        // Button to open a separate window that shows analysis history from the database.
        JButton historyButton = new JButton("View History");
        historyButton.addActionListener(e -> new HistoryFrame());

        // Button to export the most recent analysis report to a text file.
        JButton exportButton = new JButton("Export Report");
        exportButton.addActionListener(e -> exportReport());

        // ----- SPLIT PANE FOR CODE + RESULTS -----
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(codeArea),      // Top: code input
                new JScrollPane(outputArea)     // Bottom: analysis output
        );
        // Allocate 60% of the space to the code area initially.
        splitPane.setResizeWeight(0.6);

        // ----- BOTTOM PANEL WITH BUTTONS -----
        JPanel bottomPanel = new JPanel(new BorderLayout());
        // Buttons are arranged: Export (left), Analyze (center), History (right)
        bottomPanel.add(exportButton, BorderLayout.WEST);
        bottomPanel.add(analyzeButton, BorderLayout.CENTER);
        bottomPanel.add(historyButton, BorderLayout.EAST);

        // Add components to the main frame.
        add(splitPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Finally make the window visible.
        setVisible(true);
    }

    /**
     * Maps a rule type (issue type) to a severity level.
     * This is a simple heuristic categorization used to give a quick summary.
     *
     * @param type the type of the issue (e.g., "StringEquality", "MagicNumber").
     * @return "HIGH", "MEDIUM", or "LOW" severity.
     */
    private String classifySeverity(String type) {
        switch (type) {
            // HIGH severity issues are likely to cause bugs or runtime errors.
            case "ResourceLeak":
            case "OffByOneLoop":
            case "StringEquality":
            case "EmptyCatch":
                return "HIGH";

            // MEDIUM severity issues are performance or maintainability concerns.
            case "DeepNesting":
            case "ExcessiveParameters":
            case "StringConcatInLoop":
            case "MagicNumber":
                return "MEDIUM";

            // LOW severity issues are style problems or potential improvements.
            case "UnusedVariable":
            case "TodoComment":
            case "MissingDefaultInSwitch":
            default:
                return "LOW";
        }
    }

    /**
     * Performs code analysis in a background thread to avoid freezing the GUI.
     * Results are displayed in the output area and a summary is stored in the database.
     *
     * This method demonstrates:
     *  - Multithreading (using a dedicated analysis thread)
     *  - Synchronization (updating shared lastIssues within a synchronized block)
     */
    private void analyzeCode() {
        // Create a separate thread so that the GUI remains responsive while analyzing.
        Thread analysisThread = new Thread(() -> {
            String source = codeArea.getText(); // Read the code from the UI

            StringBuilder sb = new StringBuilder();

            try {
                // Run all rules through the analyzer. This may take some time.
                List<Issue> issues = analyzer.analyze(source);

                // Store the latest issues in a thread-safe manner.
                // We synchronize to prevent race conditions with exportReport().
                synchronized (issueLock) {
                    lastIssues = new ArrayList<>(issues);
                }

                // Count severities for summary.
                int high = 0, medium = 0, low = 0;
                for (Issue issue : issues) {
                    String sev = classifySeverity(issue.getType());
                    switch (sev) {
                        case "HIGH":
                            high++;
                            break;
                        case "MEDIUM":
                            medium++;
                            break;
                        default:
                            low++;
                    }
                }

                // Build the output text for the user.
                if (issues.isEmpty()) {
                    sb.append("No issues found.\n");
                } else {
                    int total = issues.size();
                    sb.append("Summary: Total Issues = ")
                            .append(total)
                            .append("  (High: ").append(high)
                            .append(", Medium: ").append(medium)
                            .append(", Low: ").append(low)
                            .append(")\n");
                    sb.append("----------------------------------------------------\n");
                    for (Issue issue : issues) {
                        String sev = classifySeverity(issue.getType());
                        sb.append("[").append(sev).append("] ")
                                .append(issue).append("\n");
                    }
                }

                // Save a compact summary (issue count + timestamp) in the database.
                // This demonstrates JDBC usage and persistence.
                int count = issues.size();
                AnalysisRecord record =
                        new AnalysisRecord(count, java.time.LocalDateTime.now());
                historyDao.save(record);

            } catch (Throwable t) {
                // If any unexpected error occurs, we print the stack trace in the output area.
                sb.append("An error occurred during analysis:\n")
                        .append(t.getClass().getName()).append(": ")
                        .append(t.getMessage()).append("\n");
                for (StackTraceElement el : t.getStackTrace()) {
                    sb.append("  at ").append(el.toString()).append("\n");
                }
            }

            // Update the GUI on the Event Dispatch Thread (EDT) for thread-safety.
            SwingUtilities.invokeLater(() -> outputArea.setText(sb.toString()));
        });

        // Start the background analysis thread.
        analysisThread.start();
    }

    /**
     * Exports the most recent analysis results to a text file.
     *
     * This method:
     *  - Uses synchronization to safely read the lastIssues list while the analysis thread might update it.
     *  - Uses JFileChooser to let the user select the output file path.
     *  - Writes a human-readable report including severity summary and detailed issues.
     */
    private void exportReport() {
        // Take a thread-safe snapshot of the latest issues.
        List<Issue> snapshot;
        synchronized (issueLock) {
            snapshot = new ArrayList<>(lastIssues);
        }

        // If no analysis has been run yet, or there were no issues, inform the user.
        if (snapshot.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No analysis results to export.\nRun 'Analyze Code' first.",
                    "Export Report",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        // File chooser to allow user to select the destination for the report.
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("codefixer_report.txt"));

        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            // User cancelled the save operation.
            return;
        }

        File file = chooser.getSelectedFile();

        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {

            // Count severity levels based on the snapshot.
            int high = 0, medium = 0, low = 0;
            for (Issue issue : snapshot) {
                String sev = classifySeverity(issue.getType());
                switch (sev) {
                    case "HIGH":
                        high++;
                        break;
                    case "MEDIUM":
                        medium++;
                        break;
                    default:
                        low++;
                }
            }
            int total = snapshot.size();

            // Write the header and summary section.
            out.println("CodeFixer AI - Analysis Report");
            out.println("======================================");
            out.println("Summary:");
            out.println("  Total Issues: " + total);
            out.println("  High:   " + high);
            out.println("  Medium: " + medium);
            out.println("  Low:    " + low);
            out.println();
            out.println("Detailed Issues:");
            out.println("--------------------------------------");

            // Write detailed information for each issue.
            for (Issue issue : snapshot) {
                String sev = classifySeverity(issue.getType());
                out.println("[" + sev + "] " + issue.toString());
            }

            // Inform the user that export was successful.
            JOptionPane.showMessageDialog(
                    this,
                    "Report exported to:\n" + file.getAbsolutePath(),
                    "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            // Handle any I/O exceptions during export.
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to export report:\n" + ex.getMessage(),
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
