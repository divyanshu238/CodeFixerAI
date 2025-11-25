package com.codefixerai.rules;

import com.codefixerai.model.Issue;
import java.util.List;

/**
 * Base contract for all static analysis rules in CodeFixerAI.
 *
 * Implementations:
 * - Inspect the given Java source code.
 * - Return a list of Issue objects describing any problems detected.
 */

public interface Rule {

    /**
     * Applies this rule to the given Java source code.
     *
     * @param sourceCode Raw Java source as a String.
     * @return A list of Issue objects reported by this rule, or an empty list if none.
     */

    List<Issue> apply(String sourceCode);
    String getName();
}
