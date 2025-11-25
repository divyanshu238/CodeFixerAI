package com.codefixerai.analyzer;

import com.codefixerai.model.Issue;
import com.codefixerai.rules.Rule;
import com.codefixerai.rules.StringEqualityRule;
import com.codefixerai.rules.OffByOneLoopRule;
import com.codefixerai.rules.ResourceLeakRule;
import com.codefixerai.rules.EmptyCatchBlockRule;
import com.codefixerai.rules.StringConcatInLoopRule;
import com.codefixerai.rules.MagicNumberRule;
import com.codefixerai.rules.UnusedVariableRule;
import com.codefixerai.rules.AlwaysTrueConditionRule;
import com.codefixerai.rules.DeepNestingRule;
import com.codefixerai.rules.MissingDefaultInSwitchRule;
import com.codefixerai.rules.ExcessiveParameterRule;
import com.codefixerai.rules.TodoCommentRule;
import java.util.ArrayList;
import java.util.List;

/**
 * Central coordinator for static analysis rules.
 *
 * This class:
 * - Keeps a list of all Rule implementations.
 * - Runs each rule against the provided source code string.
 * - Collects all detected issues into a single list.
 */

public class CodeAnalyzer {

    private final List<Rule> rules = new ArrayList<>();

    /**
     * Creates a new CodeAnalyzer and registers all available rules.
     *
     * Each rule encapsulates a specific type of bug or code smell
     * (for example: string equality with '==', magic numbers, deep nesting, etc.).
     */

    public CodeAnalyzer() {
        rules.add(new StringEqualityRule());
        rules.add(new OffByOneLoopRule());
        rules.add(new ResourceLeakRule());
        rules.add(new EmptyCatchBlockRule());
        rules.add(new StringConcatInLoopRule());
        rules.add(new MagicNumberRule());
        rules.add(new UnusedVariableRule());
        rules.add(new AlwaysTrueConditionRule());
        rules.add(new DeepNestingRule());
        rules.add(new MissingDefaultInSwitchRule());
        rules.add(new ExcessiveParameterRule());
        rules.add(new TodoCommentRule());

    }

    /**
     * Runs all registered rules on the given source code.
     *
     * @param sourceCode Raw Java source code as a single string.
     * @return A list of Issue objects reported by each Rule.
     */

    public List<Issue> analyze(String sourceCode) {
        List<Issue> allIssues = new ArrayList<>();
        for (Rule rule : rules) {
            allIssues.addAll(rule.apply(sourceCode));
        }
        return allIssues;
    }
}
