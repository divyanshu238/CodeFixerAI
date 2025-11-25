package com.codefixerai.rules;

import com.codefixerai.model.Issue;

import java.util.ArrayList;
import java.util.List;

/**
 * Rule that flags String concatenation inside loops.
 *
 * Since Strings are immutable, repeated concatenation creates unnecessary objects,
 * so StringBuilder should be used instead for better performance.
 */


public class StringConcatInLoopRule implements Rule {

    @Override
    public List<Issue> apply(String sourceCode) {
        List<Issue> issues = new ArrayList<>();

        String[] lines = sourceCode.split("\\R");
        boolean inLoop = false;
        int braceDepth = 0;

        for (int i = 0; i < lines.length; i++) {
            String rawLine = lines[i];
            String line = rawLine.trim();

            if (line.startsWith("for ") || line.startsWith("for(") ||
                    line.startsWith("while ") || line.startsWith("while(")) {
                inLoop = true;
                if (line.contains("{")) {
                    braceDepth++;
                }
                continue;
            }

            if (inLoop) {
                if (line.contains("{")) braceDepth++;
                if (line.contains("}")) {
                    braceDepth--;
                    if (braceDepth <= 0) {
                        inLoop = false;
                        braceDepth = 0;
                    }
                }

                // heuristics: "+=" and a quote -> likely string concat
                if (line.contains("+=") && line.contains("\"")) {
                    issues.add(new Issue(
                            i + 1,
                            "StringConcatInLoop",
                            "Possible inefficient String concatenation inside a loop.",
                            "Use StringBuilder or StringBuffer instead of '+=' inside loops."
                    ));
                }
            }
        }

        return issues;
    }

    @Override
    public String getName() {
        return "String Concat In Loop Rule";
    }
}
