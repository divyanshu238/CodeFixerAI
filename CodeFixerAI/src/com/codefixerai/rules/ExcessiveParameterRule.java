package com.codefixerai.rules;

import com.codefixerai.model.Issue;
import java.util.*;

/**
 * Rule that flags methods with too many parameters.
 *
 * A large parameter list can indicate poor abstraction,
 * and suggests that a data class or encapsulation might improve structure.
 */


public class ExcessiveParameterRule implements Rule {

    @Override
    public List<Issue> apply(String sourceCode) {
        List<Issue> issues = new ArrayList<>();
        String[] lines = sourceCode.split("\\R");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            if (line.matches(".*\\(.*\\).*") && line.contains(",")) {
                int commas = line.length() - line.replace(",", "").length();
                if (commas >= 4) {
                    issues.add(new Issue(
                            i + 1,
                            "ExcessiveParameters",
                            "Method has too many parameters (" + (commas + 1) + ").",
                            "Refactor using objects or builder patterns."
                    ));
                }
            }
        }
        return issues;
    }

    @Override
    public String getName() {
        return "Excessive Parameter Rule";
    }
}
