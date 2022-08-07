package org.example.tautology;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.example.tautology.operators.BinaryLogicalOperator;
import org.example.tautology.operators.UnaryLogicalOperator;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class Token {

    public static Token expression(Expression exp) {
        return new ExpressionToken(exp);
    }
    public static Token operator(BinaryLogicalOperator oper) {
        return new InfixOperatorToken(oper);
    }
    public static Token operator(UnaryLogicalOperator oper) {
        return new PrefixOperatorToken(oper);
    }

    public boolean isOperatorToken() {return false;}
    public List<Token> absorb(List<Token> tokens, int operatorIndex) {return Collections.emptyList();}
    public int getPriority() {return 0;}

    @AllArgsConstructor
    @Getter
    @ToString
    public static class InfixOperatorToken extends Token {

        private static final Map<BinaryLogicalOperator, BiFunction<Expression, Expression, Expression>> map;
        static {
            map = new HashMap<>();
            map.put(BinaryLogicalOperator.OR, Expression::or);
            map.put(BinaryLogicalOperator.AND, Expression::and);
            map.put(BinaryLogicalOperator.IMPLICATION, Expression::implication);
            map.put(BinaryLogicalOperator.DOUBLE_IMPLICATION, Expression::doubleImplication);
        }
        private BinaryLogicalOperator operator;

        @Override
        public boolean isOperatorToken() {
            return true;
        }

        @Override
        public List<Token> absorb(List<Token> tokens, int operatorIndex) {

            List<Token> newTokens = new ArrayList<>(tokens.subList(0, operatorIndex - 1));

            ExpressionToken left = (ExpressionToken) tokens.get(operatorIndex - 1);
            ExpressionToken right = (ExpressionToken) tokens.get(operatorIndex + 1);
            newTokens.add(new ExpressionToken(map.get(operator).apply(left.getExpression(), right.getExpression())));

            newTokens.addAll(tokens.subList(operatorIndex + 2, tokens.size()));
            return newTokens;
        }

        @Override
        public int getPriority() {
            return operator.getPriority();
        }
    }

    @AllArgsConstructor
    @Getter
    @ToString
    public static class PrefixOperatorToken extends Token {

        private static final Map<UnaryLogicalOperator, Function<Expression, Expression>> map;
        static {
            map = new HashMap<>();
            map.put(UnaryLogicalOperator.NOT, Expression::not);
        }
        private UnaryLogicalOperator operator;

        @Override
        public boolean isOperatorToken() {
            return true;
        }

        @Override
        public List<Token> absorb(List<Token> tokens, int operatorIndex) {

            List<Token> newTokens = new ArrayList<>(tokens.subList(0, operatorIndex));

            ExpressionToken right = (ExpressionToken) tokens.get(operatorIndex + 1);
            newTokens.add(new ExpressionToken(map.get(operator).apply(right.getExpression())));

            newTokens.addAll(tokens.subList(operatorIndex + 2, tokens.size()));
            return newTokens;
        }

        @Override
        public int getPriority() {
            return operator.getPriority();
        }
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class ExpressionToken extends Token {
        private Expression expression;

    }
}
