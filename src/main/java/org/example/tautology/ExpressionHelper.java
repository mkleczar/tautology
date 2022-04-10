package org.example.tautology;

import java.util.Optional;
import java.util.Set;

public class ExpressionHelper {

    public static Set<String> collectVariables(Expression expression) {
        return Optional.of(new ExpressionCollectingVariablesVisitor())
                .map(v -> {expression.accept(v); return v;})
                .map(ExpressionCollectingVariablesVisitor::getVariableNames)
                .orElse(Set.of());
    }
}
