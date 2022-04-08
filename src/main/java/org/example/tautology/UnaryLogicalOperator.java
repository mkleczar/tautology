package org.example.tautology;

import java.util.function.UnaryOperator;

public enum UnaryLogicalOperator implements UnaryOperator<Boolean> {

    NOT(p -> !p, "\u00AC", 50); // !

    private final UnaryOperator<Boolean> operator;
    private final String symbol;
    private final int priority;

    UnaryLogicalOperator(UnaryOperator<Boolean> operator, String symbol, int priority) {
        this.operator = operator;
        this.symbol = symbol;
        this.priority = priority;
    }

    @Override
    public Boolean apply(Boolean p) {
        return operator.apply(p);
    }

    public String getSymbol() {
        return symbol;
    }

    public int getPriority() {
        return priority;
    }
}
