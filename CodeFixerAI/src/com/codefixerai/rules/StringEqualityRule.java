package com.codefixerai.rules;

import com.codefixerai.model.Issue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rule that detects usage of '==' for comparing String values.
 *
 * In Java, Strings should be compared using equals(...) instead of '==',
 * because '==' only compares object references, not the actual contents.
 */

public class StringEqualityRule implements Rule {

    @Override
    public List<Issue> apply(String sourceCode) {
        List<Issue> issues = new ArrayList<>();

        String[] lines = sourceCode.split("\\R");

        // 1) Collect names of String variables
        Set<String> stringVars = new HashSet<>();
        Pattern declPattern = Pattern.compile("\\bString\\s+([a-zA-Z_][a-zA-Z0-9_]*)");
        for (String line : lines) {
            Matcher m = declPattern.matcher(line);
            while (m.find()) {
                stringVars.add(m.group(1));
            }
        }

        // 2) Look for == comparisons involving those String variables
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (!line.contains("==")) continue;

            for (String var : stringVars) {
                // very simple checks around ==
                if (line.contains(var + " ==") || line.contains("== " + var) ||
                        line.contains(var + "==") || line.contains("==" + var)) {

                    issues.add(new Issue(
                            i + 1,
                            "StringEquality",
                            "Possible String comparison using '==' for variable '" + var + "'.",
                            "Use " + var + ".equals(...) or .equalsIgnoreCase(...) instead of '=='."
                    ));
                    break; // avoid duplicates for same line
                }
            }
        }

        return issues;
    }

    @Override
    public String getName() {
        return "String Equality Rule";
    }
}
