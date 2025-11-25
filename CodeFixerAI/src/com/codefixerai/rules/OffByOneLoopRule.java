package com.codefixerai.rules;

import com.codefixerai.model.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rule that detects potential off-by-one errors in loop boundaries.
 *
 * These subtle mistakes commonly occur in for-loops and can result in
 * skipping elements, accessing out-of-range indexes, or unintended iteration counts.
 */


public class OffByOneLoopRule implements Rule {

    private static final Pattern FOR_PATTERN =
            Pattern.compile("\\bfor\\s*\\(([^)]*)\\)");

    @Override
    public List<Issue> apply(String sourceCode) {
        List<Issue> issues = new ArrayList<>();
        String[] lines = sourceCode.split("\\R");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            Matcher m = FOR_PATTERN.matcher(line);
            if (m.find()) {
                String header = m.group(1); // stuff inside the for(...)

                if (header.contains("<=") &&
                        (header.contains(".length") || header.contains(".size("))) {

                    issues.add(new Issue(
                            i + 1,
                            "OffByOneLoop",
                            "Possible off-by-one error: loop uses '<=' with length/size.",
                            "Use '<' instead of '<=' in the loop condition to avoid going out of bounds."
                    ));
                }
            }
        }
        return issues;
    }

    @Override
    public String getName() {
        return "Off-by-one Loop Rule";
    }
}
