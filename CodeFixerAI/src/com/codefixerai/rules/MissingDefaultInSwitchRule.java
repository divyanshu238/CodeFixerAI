package com.codefixerai.rules;

import com.codefixerai.model.Issue;
import java.util.*;

/**
 * Rule that identifies switch statements missing a default case.
 *
 * A default branch ensures behavior is defined for unexpected values
 * and prevents silent failures when new enum or constant values are added.
 */


public class MissingDefaultInSwitchRule implements Rule {

    @Override
    public List<Issue> apply(String sourceCode) {
        List<Issue> issues = new ArrayList<>();

        String[] lines = sourceCode.split("\\R");
        boolean inSwitch = false;
        boolean hasDefault = false;
        int switchLine = -1;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            if (line.startsWith("switch")) {
                inSwitch = true;
                hasDefault = false;
                switchLine = i + 1;
            }

            if (inSwitch && line.startsWith("default")) {
                hasDefault = true;
            }

            if (inSwitch && line.contains("}")) {
                if (!hasDefault) {
                    issues.add(new Issue(
                            switchLine,
                            "MissingDefaultInSwitch",
                            "Switch statement has no default case.",
                            "Add a default case to handle unexpected values."
                    ));
                }
                inSwitch = false;
            }
        }
        return issues;
    }

    @Override
    public String getName() {
        return "Missing Default in Switch Rule";
    }
}
