package com.codefixerai.model;

import java.time.LocalDateTime;

/**
 * Represents a single stored analysis run.
 *
 * Typical fields:
 * - The analyzed source code or snippet.
 * - The number of issues found.
 * - Timestamp when the analysis was performed.
 */

public class AnalysisRecord {

    private final int issueCount;
    private final LocalDateTime analyzedAt;

    public AnalysisRecord(int issueCount, LocalDateTime analyzedAt) {
        this.issueCount = issueCount;
        this.analyzedAt = analyzedAt;
    }

    // Standard getters and setters for accessing the data in this record.

    public int getIssueCount() {
        return issueCount;
    }

    public LocalDateTime getAnalyzedAt() {
        return analyzedAt;
    }
}
