package com.codefixerai.model;

/**
 * Describes a single problem found in the source code by a Rule.
 *
 * Typical data includes:
 * - Issue type or key.
 * - Human-readable description and suggestion.
 * - Location information (line/column) if available.
 */

public class Issue {
    private final int line;
    private final String type;
    private final String message;
    private final String suggestedFix;

    public Issue(int line, String type, String message, String suggestedFix) {
        this.line = line;
        this.type = type;
        this.message = message;
        this.suggestedFix = suggestedFix;
    }

    // Basic data holder (POJO) for a detected issue.

    public int getLine() { return line; }
    public String getType() { return type; }
    public String getMessage() { return message; }
    public String getSuggestedFix() { return suggestedFix; }

    @Override
    public String toString() {
        return "Line " + line + " [" + type + "]: " + message +
                " | Fix: " + suggestedFix;
    }
}
