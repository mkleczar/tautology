package org.example.tautology.visitor;

import org.example.tautology.Expression;

import java.util.HashSet;
import java.util.Set;

public class ExpressionCollectingVariablesVisitor extends ExpressionAbstractVisitor {

    private final Set<String> variableNames = new HashSet<>();

    @Override
    public void visiting(Expression.Variable ex) {
        variableNames.add(ex.getName());
    }

    public Set<String> getVariableNames() {
        return variableNames;
    }
}
