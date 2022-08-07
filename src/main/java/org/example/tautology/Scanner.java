package org.example.tautology;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Scanner {

    public static Expression scan(List<Token> tokens) {
        return null;
    }

    public static Expression scanNoBrackets(List<Token> tokens) {
        if (tokens.size() == 1) {
            return ((Token.ExpressionToken) tokens.get(0)).getExpression();
        }
        int priorityIndex = findHighestPriorityIndex(tokens);
        return scanNoBrackets(tokens.get(priorityIndex).absorb(tokens, priorityIndex));
    }

    private static int findHighestPriorityIndex(List<Token> tokens) {
        int priority = -1;
        int priorityIndex = 0;
        for (int t = 0; t < tokens.size(); ++t) {
            if (tokens.get(t).getPriority() > priority) {
                priorityIndex = t;
                priority = tokens.get(t).getPriority();
            }
        }
        return priorityIndex;
    }
}
