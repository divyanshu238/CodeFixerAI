package com.codefixerai.rules;

import com.codefixerai.model.Issue;
import java.util.*;

/**
 * Rule that detects conditional expressions that are always true or always false.
 *
 * These conditions make code misleading and can hide logic errors,
 * because the branch outcome never changes at runtime.
 */

public class AlwaysTrueConditionRule implements Rule {

    @Override
    public List<Issue> apply(String sourceCode) {
        List<Issue> issues = new ArrayList<>();
        String[] lines = sourceCode.split("\\R");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            if (line.matches("if\\s*\\(true\\)|while\\s*\\(true\\)")) {
                issues.add(new Issue(
                        i + 1,
                        "AlwaysTrueCondition",
                        "Condition is always true.",
                        "Replace with meaningful condition."
                ));
            }

            if (line.matches("if\\s*\\(false\\)|while\\s*\\(false\\)")) {
                issues.add(new Issue(
                        i + 1,
                        "AlwaysFalseCondition",
                        "Condition is always false. Code will never execute.",
                        "Remove or fix condition."
                ));
            }
        }
        return issues;
    }

    @Override
    public String getName() {
        return "Always True/False Condition Rule";
    }
}
