package org.example.tautology;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Expression {

    public static Expression variable(String name) {
        return new Variable(name);
    }
    public static Expression constTrue() {
        return new Const(true);
    }
    public static Expression constFalse() {
        return new Const(false);
    }
    public static Expression not(Expression expression) {
        return new UnaryExpression(UnaryLogicalOperator.NOT, expression);
    }
    public static Expression or(Expression ... expressions) {
        return new BinaryExpression(BinaryLogicalOperator.OR, expressions);
    }
    public static Expression and(Expression ... expressions) {
        return new BinaryExpression(BinaryLogicalOperator.AND, expressions);
    }
    public static Expression implication(Expression hypothesis, Expression conclusion) {
        return new BinaryExpression(BinaryLogicalOperator.IMPLICATION, hypothesis, conclusion);
    }
    public static Expression doubleImplication(Expression antecedent, Expression consequent) {
        return new BinaryExpression(BinaryLogicalOperator.DOUBLE_IMPLICATION, antecedent, consequent);
    }

    public abstract Boolean validate(Context context);
    public abstract boolean isVariable();
    public abstract String asText();

    @Override
    public String toString() {
        return asText();
    }


    private static class Variable extends Expression {
        private final String name;

        private Variable(String name) {
            this.name = name;
        }

        @Override
        public Boolean validate(Context context) {
            return context.check(name);
        }

        @Override
        public boolean isVariable() {
            return true;
        }

        @Override
        public String asText() {
            return name;
        }
    }

    private static class Const extends Expression {
        private final boolean value;

        public Const(boolean value) {
            this.value = value;
        }

        @Override
        public Boolean validate(Context context) {
            return value;
        }

        @Override
        public boolean isVariable() {
            return false;
        }

        @Override
        public String asText() {
            return value ? "1" : "0";
        }
    }

    private static class UnaryExpression extends Expression {
        private final UnaryLogicalOperator operator;
        private final Expression expression;

        public UnaryExpression(UnaryLogicalOperator operator, Expression expression) {
            this.operator = operator;
            this.expression = expression;
        }

        @Override
        public Boolean validate(Context context) {
            return operator.apply(expression.validate(context));
        }

        @Override
        public boolean isVariable() {
            return false;
        }

        @Override
        public String asText() {
            return operator.getSymbol() + expression.toString();
        }
    }

    private static class BinaryExpression extends Expression {
        private final BinaryLogicalOperator operator;
        private final List<Expression> expressions;

        public BinaryExpression(BinaryLogicalOperator operator, Expression ... expressions) {
            this.operator = operator;
            this.expressions = List.of(expressions);
        }

        @Override
        public Boolean validate(Context context) {
            return expressions.stream()
                    .map(e -> e.validate(context))
                    .reduce(operator)
                    .orElse(false);
        }

        @Override
        public boolean isVariable() {
            return false;
        }

        @Override
        public String asText() {
            return expressions.stream()
                    .map(Expression::toString)
                    .collect(Collectors.joining(operator.getSymbol(), "(", ")"));
        }
    }
}
