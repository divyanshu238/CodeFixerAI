package com.codefixerai.rules;

import com.codefixerai.model.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rule that flags "magic numbers" in the source code.
 *
 * Magic numbers are hard-coded numeric literals that should be replaced
 * with named constants to improve readability and maintainability.
 */

public class MagicNumberRule implements Rule {

    private static final Pattern NUMBER_PATTERN =
            Pattern.compile("\\b(\\d{2,}|[3-9])\\b");

    @Override
    public List<Issue> apply(String sourceCode) {
        List<Issue> issues = new ArrayList<>();
        String[] lines = sourceCode.split("\\R");

        for (int i = 0; i < lines.length; i++) {
            String trimmed = lines[i].trim();
            if (trimmed.startsWith("//")) continue; // ignore comments

            Matcher m = NUMBER_PATTERN.matcher(trimmed);
            if (m.find()) {
                issues.add(new Issue(
                        i + 1,
                        "MagicNumber",
                        "Magic number '" + m.group(1) + "' found in code.",
                        "Consider extracting this value into a named constant for better readability."
                ));
            }
        }

        return issues;
    }

    @Override
    public String getName() {
        return "Magic Number Rule";
    }
}
