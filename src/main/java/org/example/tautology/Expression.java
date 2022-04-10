package org.example.tautology;

import org.example.tautology.context.Context;
import org.example.tautology.operators.BinaryLogicalOperator;
import org.example.tautology.operators.UnaryLogicalOperator;
import org.example.tautology.visitor.ExpressionVisitor;

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
    public abstract String asText();
    public abstract void accept(ExpressionVisitor visitor);


    @Override
    public String toString() {
        return asText();
    }


    public static class Variable extends Expression {
        private final String name;

        private Variable(String name) {
            this.name = name;
        }

        @Override
        public Boolean validate(Context context) {
            return context.check(name);
        }

        @Override
        public void accept(ExpressionVisitor visitor) {
            visitor.visiting(this);
        }

        @Override
        public String asText() {
            return name;
        }

        public String getName() {
            return this.name;
        }
    }

    public static class Const extends Expression {
        private final boolean value;

        private Const(boolean value) {
            this.value = value;
        }

        @Override
        public Boolean validate(Context context) {
            return value;
        }

        @Override
        public void accept(ExpressionVisitor visitor) {
            visitor.visiting(this);
        }

        @Override
        public String asText() {
            return value ? "1" : "0";
        }
    }

    public static class UnaryExpression extends Expression {
        private final UnaryLogicalOperator operator;
        private final Expression expression;

        private UnaryExpression(UnaryLogicalOperator operator, Expression expression) {
            this.operator = operator;
            this.expression = expression;
        }

        @Override
        public Boolean validate(Context context) {
            return operator.apply(expression.validate(context));
        }

        @Override
        public void accept(ExpressionVisitor visitor) {
            expression.accept(visitor);
            visitor.visiting(this);
        }

        @Override
        public String asText() {
            return operator.getSymbol() + expression.toString();
        }
    }

    public static class BinaryExpression extends Expression {
        private final BinaryLogicalOperator operator;
        private final List<Expression> expressions;

        private BinaryExpression(BinaryLogicalOperator operator, Expression ... expressions) {
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
        public void accept(ExpressionVisitor visitor) {
            expressions.forEach(expression -> expression.accept(visitor));
            visitor.visiting(this);
        }

        @Override
        public String asText() {
            return expressions.stream()
                    .map(Expression::toString)
                    .collect(Collectors.joining(operator.getSymbol(), "(", ")"));
        }
    }
}
