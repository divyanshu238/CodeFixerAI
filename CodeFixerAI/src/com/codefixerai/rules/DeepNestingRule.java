package com.codefixerai.rules;

import com.codefixerai.model.Issue;
import java.util.*;

/**
 * Rule that identifies methods or blocks with excessive nesting depth.
 *
 * Deeply nested if/else or loop structures reduce readability and
 * suggest that the code should be refactored into smaller methods.
 */

public class DeepNestingRule implements Rule {

    @Override
    public List<Issue> apply(String sourceCode) {
        List<Issue> issues = new ArrayList<>();
        int depth = 0;
        String[] lines = sourceCode.split("\\R");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            for (char c : line.toCharArray()) {
                if (c == '{') depth++;
                if (c == '}') depth--;
            }

            if (depth > 3) {
                issues.add(new Issue(
                        i + 1,
                        "DeepNesting",
                        "Code nesting is too deep (" + depth + " levels).",
                        "Refactor using methods, early returns, or guard clauses."
                ));
            }
        }
        return issues;
    }

    @Override
    public String getName() {
        return "Deep Nesting Rule";
    }
}
