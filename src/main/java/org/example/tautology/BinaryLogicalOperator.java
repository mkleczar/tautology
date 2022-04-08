package org.example.tautology;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;


public enum BinaryLogicalOperator implements BinaryOperator<Boolean> {
    AND(
            (p,q) -> p && q,
            "\u2227", // &
            40),
    OR(
            (p,q) -> p || q,
            "\u2228", // |
            30),
    IMPLICATION(
            (p,q) -> !p || q,
            "\u21D2", // ->
            20),
    DOUBLE_IMPLICATION(
            (p,q) -> p == q,
            "\u21D4", // <->
            10);

    private final BiFunction<Boolean, Boolean, Boolean> operator;
    private final String symbol;
    private final int priority;

    BinaryLogicalOperator(BinaryOperator<Boolean> operator, String symbol, int priority) {
        this.operator = operator;
        this.symbol = symbol;
        this.priority = priority;
    }

    @Override
    public Boolean apply(Boolean p, Boolean q) {
        return operator.apply(p, q);
    }

    public String getSymbol() {
        return symbol;
    }

    public int getPriority() {
        return priority;
    }
}
