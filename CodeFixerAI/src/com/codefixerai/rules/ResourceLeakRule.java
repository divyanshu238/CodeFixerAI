package com.codefixerai.rules;

import com.codefixerai.model.Issue;

import java.util.*;

/**
 * Rule that identifies opened resources which are not properly closed.
 *
 * Resources such as files, streams, and connections must be released,
 * otherwise they may cause memory leaks or exhaustion of system handles.
 */

public class ResourceLeakRule implements Rule {

    @Override
    public List<Issue> apply(String sourceCode) {
        List<Issue> issues = new ArrayList<>();

        String[] lines = sourceCode.split("\\R");

        Map<String, Integer> resourceVars = new HashMap<>();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            String[] types = {
                    "Scanner", "FileInputStream", "FileReader",
                    "BufferedReader", "InputStreamReader"
            };

            for (String type : types) {
                String marker = type + " ";
                int idx = line.indexOf(marker);
                if (idx >= 0 && line.contains("new " + type)) {
                    // grab variable name after type
                    String rest = line.substring(idx + marker.length()).trim();
                    String varName = rest.split("[\\s=;]")[0];
                    resourceVars.put(varName, i + 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : resourceVars.entrySet()) {
            String var = entry.getKey();
            int lineNo = entry.getValue();
            if (!sourceCode.contains(var + ".close(")) {
                issues.add(new Issue(
                        lineNo,
                        "ResourceLeak",
                        "Resource '" + var + "' may not be closed.",
                        "Call " + var + ".close() (prefer try-with-resources) to avoid resource leak."
                ));
            }
        }

        return issues;
    }

    @Override
    public String getName() {
        return "Resource Leak Rule";
    }
}
