package org.example.tautology;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.tautology.exception.TokenNotSupportedByParser;
import org.example.tautology.operators.BinaryLogicalOperator;
import org.example.tautology.operators.UnaryLogicalOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum Tokens {
    LEFT_BRACKET(
            buf -> buf.startsWith("("),
            buf -> new ConversionResult(Token.leftBracket(), buf.substring(1))),
    RIGHT_BRACKET(
            buf -> buf.startsWith(")"),
            buf -> new ConversionResult(Token.rightBracket(), buf.substring(1))),
    NEGATION(
            buf -> buf.startsWith("~"),
            buf -> new ConversionResult(Token.operator(UnaryLogicalOperator.NOT), buf.substring(1))),
    IMPLICATION(
            buf -> buf.startsWith("=>"),
            buf -> new ConversionResult(Token.operator(BinaryLogicalOperator.IMPLICATION), buf.substring(2))),
    DOUBLE_IMPLICATION(
            buf -> buf.startsWith("<=>"),
            buf -> new ConversionResult(Token.operator(BinaryLogicalOperator.DOUBLE_IMPLICATION), buf.substring(3))),
    ALTERNATIVE(
            buf -> buf.startsWith("|"),
            buf -> new ConversionResult(Token.operator(BinaryLogicalOperator.OR), buf.substring(1))),
    CONJUNCTION(
            buf -> buf.startsWith("&"),
            buf -> new ConversionResult(Token.operator(BinaryLogicalOperator.AND), buf.substring(1))),
    VARIABLE(
            buf -> buf.matches("^[a-z].*"),
            buf -> new ConversionResult(Token.expression(Expression.variable(buf.substring(0,1))), buf.substring(1))),
    CONST_TRUE(
            buf -> buf.startsWith("1"),
            buf -> new ConversionResult(Token.constTrue(), buf.substring(1))),
    CONST_FALSE(
            buf -> buf.startsWith("0"),
            buf -> new ConversionResult(Token.constFalse(), buf.substring(1)))
    ;

    private Predicate<String> filter;
    private Function<String, ConversionResult> converter;

    Tokens(Predicate<String> filter, Function<String, ConversionResult> converter) {
        this.filter = filter;
        this.converter = converter;
    }

    public static List<Token> parse(String strVal) {
        StringBuilder str = new StringBuilder(strVal.replaceAll("\\s+", ""));
        List<Token> result = new ArrayList<>();
        while (!str.isEmpty()) {
            Stream.of(Tokens.values())
                    .filter(t -> t.filter.test(str.toString()))
                    .map(t -> t.converter.apply(str.toString()))
                    .findAny()
                    .ifPresentOrElse(res -> {
                                str.setLength(0);
                                str.append(res.reminder);
                                result.add(res.token);
                            },
                            () -> {
                                throw new TokenNotSupportedByParser(str.toString());
                            }
                    );
        }
        return result;
    }

    @Getter
    @AllArgsConstructor
    public static class ConversionResult {
        Token token;
        String reminder;
    }
}
