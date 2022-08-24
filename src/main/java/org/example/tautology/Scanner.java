package org.example.tautology;

import lombok.extern.slf4j.Slf4j;
import org.example.tautology.utils.ListNode;

import java.util.Comparator;
import java.util.List;

@Slf4j
public class Scanner {

    public static Expression scan(List<Token> tokens) {
        if (tokens.size() == 1) {
            return ((Token.ExpressionToken) tokens.get(0)).getExpression();
        }
        return null;
    }

    public static Token.ExpressionToken scanNoBrackets(List<Token> tokens) {
        if (tokens.size() == 1) {
            return (Token.ExpressionToken) tokens.get(0);
        }
        ListNode<Token> token = ListNode.findMaxByComparator(tokens, Comparator.comparing(Token::getPriority));
        return scanNoBrackets(token.getValue().absorb(token));
    }

}
