package com.codefixerai.rules;

import com.codefixerai.model.Issue;

import java.util.ArrayList;
import java.util.List;

/**
 * Rule that detects empty catch blocks.
 *
 * Empty catch blocks silently swallow exceptions, making debugging difficult
 * and potentially hiding runtime failures that should be logged or handled.
 */


public class EmptyCatchBlockRule implements Rule {

    @Override
    public List<Issue> apply(String sourceCode) {
        List<Issue> issues = new ArrayList<>();
        String[] lines = sourceCode.split("\\R");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (!line.contains("catch")) continue;

            if (line.matches(".*catch\\s*\\([^)]*\\)\\s*\\{\\s*\\}.*")) {
                issues.add(new Issue(
                        i + 1,
                        "EmptyCatch",
                        "Empty catch block detected.",
                        "Handle the exception or at least log it instead of leaving the block empty."
                ));
                continue;
            }

            int j = i + 1;
            boolean hasCode = false;
            boolean inBlock = false;

            while (j < lines.length) {
                String l = lines[j].trim();
                if (l.isEmpty() || l.startsWith("//")) {
                    j++;
                    continue;
                }
                if (!inBlock && l.equals("{")) {
                    inBlock = true;
                    j++;
                    continue;
                }
                if (l.equals("}")) {
                    if (!hasCode) {
                        issues.add(new Issue(
                                i + 1,
                                "EmptyCatch",
                                "Empty catch block detected.",
                                "Handle the exception or at least log it instead of leaving the block empty."
                        ));
                    }
                    break;
                }
                hasCode = true;
                break;
            }
        }

        return issues;
    }

    @Override
    public String getName() {
        return "Empty Catch Block Rule";
    }
}
