package org.example.tautology;

import lombok.extern.slf4j.Slf4j;
import org.example.tautology.utils.ListNode;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
public class Scanner {

    public static Expression scan(List<Token> tokens) {
        if (tokens.size() == 1) {
            return ((Token.ExpressionToken) tokens.get(0)).getExpression();
        }
        Optional<ListNode<Token>> leftOpt = ListNode.findLast(tokens, Token::isLeftBracket);

        if (leftOpt.isPresent()) {
            ListNode<Token> left = leftOpt.get();
            ListNode<Token> right = left.next(Token::isRightBracket).get();
            List<Token> between = ListNode.between(left, right);

            List<Token> newTokens = left.getListBefore();
            newTokens.add(scanNoBrackets(between));
            newTokens.addAll(right.getListAfter());
            return scan(newTokens);
        }
        return scanNoBrackets(tokens).getExpression();
    }

    public static Token.ExpressionToken scanNoBrackets(List<Token> tokens) {
        if (tokens.size() == 1) {
            return (Token.ExpressionToken) tokens.get(0);
        }
        ListNode<Token> token = ListNode.findMaxByComparator(tokens, Comparator.comparing(Token::getPriority));
        return scanNoBrackets(token.getValue().absorb(token));
    }

}
