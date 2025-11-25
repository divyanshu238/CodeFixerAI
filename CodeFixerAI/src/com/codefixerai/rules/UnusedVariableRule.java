package com.codefixerai.rules;

import com.codefixerai.model.Issue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rule that identifies variables that are declared but never used.
 *
 * Unused variables clutter code, waste memory, and often signal leftover
 * logic, incomplete refactoring, or incorrect implementation.
 */


public class UnusedVariableRule implements Rule {

    @Override
    public List<Issue> apply(String sourceCode) {
        List<Issue> issues = new ArrayList<>();

        String[] lines = sourceCode.split("\\R");
        Map<String, Integer> declLines = new HashMap<>();

        String[] types = {"int", "String", "double", "float", "char", "long", "boolean"};

        // Find declarations
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            for (String type : types) {
                if (line.startsWith(type + " ")) {
                    String rest = line.substring(type.length()).trim();
                    if (!rest.isEmpty()) {
                        String varName = rest.split("[\\s=;]")[0];
                        if (!varName.isEmpty()) {
                            declLines.put(varName, i + 1);
                        }
                    }
                }
            }
        }

        // Check if used somewhere else
        for (Map.Entry<String, Integer> entry : declLines.entrySet()) {
            String var = entry.getKey();
            int declLine = entry.getValue();
            boolean used = false;

            for (int i = 0; i < lines.length; i++) {
                if (i + 1 == declLine) continue;

                if (lines[i].contains(var)) {
                    used = true;
                    break;
                }
            }

            if (!used) {
                issues.add(new Issue(
                        declLine,
                        "UnusedVariable",
                        "Variable '" + var + "' is declared but never used.",
                        "Remove or use the variable in logic."
                ));
            }
        }

        return issues;
    }

    @Override
    public String getName() {
        return "Unused Variable Rule";
    }
}
