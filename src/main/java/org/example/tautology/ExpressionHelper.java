package org.example.tautology;

import org.example.tautology.context.ContextAllPossibleValuesGenerator;

import java.util.Optional;
import java.util.Set;

public class ExpressionHelper {

    public static Set<String> collectVariables(Expression expression) {
        return Optional.of(new ExpressionCollectingVariablesVisitor())
                .map(v -> {expression.accept(v); return v;})
                .map(ExpressionCollectingVariablesVisitor::getVariableNames)
                .orElse(Set.of());
    }

    public static boolean isTautology(Expression expression) {
        Set<String> params = collectVariables(expression);
        ContextAllPossibleValuesGenerator generator = new ContextAllPossibleValuesGenerator(params);
        return generator.stream().allMatch(expression::validate);
    }
}
