package org.example.tautology;

import java.util.List;

public abstract class Expression {

    public static Expression variable(String name) {
        return new Variable(name);
    }
    public static Expression not(Expression expression) {
        return new Negation(expression);
    }
    public static Expression or(Expression ... expressions) {
        return new Alternative(expressions);
    }

    public abstract Boolean validate(Context context);




    private static class Variable extends Expression {

        private final String name;

        private Variable(String name) {
            this.name = name;
        }

        @Override
        public Boolean validate(Context context) {
            return context.check(name);
        }
    }

    private static class Negation extends Expression {
        private final Expression expression;

        private Negation(Expression expression) {
            this.expression = expression;
        }

        @Override
        public Boolean validate(Context context) {
            return !expression.validate(context);
        }
    }

    private static class Alternative extends Expression {
        private final List<Expression> expressions;

        private Alternative(Expression ... expressions) {
            this.expressions = List.of(expressions);
        }

        @Override
        public Boolean validate(Context context) {
            return expressions.stream()
                    .map(e -> e.validate(context))
                    .reduce(false, Boolean::logicalOr, Boolean::logicalOr);
        }
    }
}
