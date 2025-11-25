package com.codefixerai.rules;

import com.codefixerai.model.Issue;
import java.util.*;

/**
 * Rule that detects TODO comments left in source code.
 *
 * TODO markers indicate unfinished work and should be addressed,
 * documented, or resolved before completion or release.
 */


public class TodoCommentRule implements Rule {

    @Override
    public List<Issue> apply(String sourceCode) {
        List<Issue> issues = new ArrayList<>();
        String[] lines = sourceCode.split("\\R");

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("TODO")) {
                issues.add(new Issue(
                        i + 1,
                        "TodoComment",
                        "TODO comment detected — unfinished implementation.",
                        "Complete the pending work or remove the TODO marker."
                ));
            }
        }
        return issues;
    }

    @Override
    public String getName() {
        return "TODO Comment Rule";
    }
}
