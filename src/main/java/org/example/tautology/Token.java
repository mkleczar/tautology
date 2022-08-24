package org.example.tautology;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.example.tautology.operators.BinaryLogicalOperator;
import org.example.tautology.operators.UnaryLogicalOperator;
import org.example.tautology.utils.ListNode;

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
    public static Token leftBracket() {
        return new LeftBracket();
    }
    public static Token rightBracket() {
        return new RightBracket();
    }

    public boolean isOperatorToken() {return false;}
    public List<Token> absorb(ListNode<Token> token) {return Collections.emptyList();}
    public int getPriority() {return 0;}
    public boolean isLeftBracket() {return false;}
    public boolean isRightBracket() {return false;}

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
        public List<Token> absorb(ListNode<Token> token) {

            ListNode<Token> tokenBefore = token.prevNode();
            ListNode<Token> tokenAfter = token.nextNode();

            List<Token> newTokens = tokenBefore.getListBefore();

            ExpressionToken left = (ExpressionToken) tokenBefore.getValue();
            ExpressionToken right = (ExpressionToken) tokenAfter.getValue();
            newTokens.add(new ExpressionToken(map.get(operator).apply(left.getExpression(), right.getExpression())));

            newTokens.addAll(tokenAfter.getListAfter());
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
        public List<Token> absorb(ListNode<Token> token) {
            ListNode<Token> tokenAfter = token.nextNode();

            List<Token> newTokens = token.getListBefore();
            ExpressionToken right = (ExpressionToken) tokenAfter.getValue();
            newTokens.add(new ExpressionToken(map.get(operator).apply(right.getExpression())));

            newTokens.addAll(tokenAfter.getListAfter());
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

    public static class LeftBracket extends Token {
        public boolean isLeftBracket() {return true;}
    }

    public static class RightBracket extends Token {
        public boolean isRightBracket() {return true;}
    }
}
