package org.example.tautology;

import lombok.extern.slf4j.Slf4j;
import org.example.tautology.operators.BinaryLogicalOperator;
import org.example.tautology.operators.UnaryLogicalOperator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.tautology.Expression.*;
import static org.example.tautology.Token.*;

@Slf4j
public class ScannerTest {


    @ParameterizedTest
    @MethodSource("scannerTestArguments")
    public void testScanner(List<Token> tokens, Expression expression) {
        Expression ex = Scanner.scan(tokens);
        //log.info("Ex: {}", ex);
        assertThat(ex.asText()).isEqualTo(expression.asText());
    }


    public static Stream<Arguments> scannerTestArguments() {
        return Stream.of(
                Arguments.of(List.of(
                                operator(UnaryLogicalOperator.NOT),
                                leftBracket(),
                                expression(variable("p")),
                                operator(BinaryLogicalOperator.OR),
                                expression(variable("q")),
                                rightBracket()
                        ),
                        not(or(variable("p"), variable("q")))
                ),
                Arguments.of(List.of(
                                leftBracket(),
                                expression(variable("p")),
                                operator(BinaryLogicalOperator.OR),
                                expression(variable("q")),
                                rightBracket(),
                                operator(BinaryLogicalOperator.AND),
                                leftBracket(),
                                expression(variable("r")),
                                operator(BinaryLogicalOperator.OR),
                                expression(variable("s")),
                                rightBracket()
                        ),
                        and(or(variable("p"), variable("q")), or(variable("r"), variable("s")))
                ),
                Arguments.of(List.of(
                                expression(variable("p")),
                                operator(BinaryLogicalOperator.AND),
                                leftBracket(),
                                leftBracket(),
                                expression(variable("q")),
                                operator(BinaryLogicalOperator.OR),
                                expression(variable("r")),
                                rightBracket(),
                                operator(BinaryLogicalOperator.AND),
                                expression(variable("s")),
                                rightBracket()
                        ),
                        and(variable("p"), and(or(variable("q"), variable("r")), variable("s")))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("scannerNoBracketsTestArguments")
    public void testNoBracketsScanner(List<Token> tokens, Expression expression) {
        Expression ex = Scanner.scanNoBrackets(tokens).getExpression();
        // log.info("Ex: {}", ex);
        assertThat(ex.asText()).isEqualTo(expression.asText());
    }

    public static Stream<Arguments> scannerNoBracketsTestArguments() {
        return Stream.of(
                Arguments.of(
                        List.of(expression(variable("p"))),
                        variable("p")
                ),
                Arguments.of(
                        List.of(
                                operator(UnaryLogicalOperator.NOT),
                                expression(variable("p"))),
                        not(variable("p"))
                ),
                Arguments.of(
                        List.of(
                                expression(variable("p")),
                                operator(BinaryLogicalOperator.OR),
                                expression(variable("q"))
                        ),
                        or(variable("p"), variable("q"))
                ),
                Arguments.of(List.of(
                                expression(variable("p")),
                                operator(BinaryLogicalOperator.OR),
                                expression(variable("q")),
                                operator(BinaryLogicalOperator.AND),
                                expression(variable("r"))
                        ),
                        or(variable("p"), and(variable("q"), variable("r")))
                ),
                Arguments.of(List.of(
                                expression(variable("p")),
                                operator(BinaryLogicalOperator.AND),
                                expression(variable("q")),
                                operator(BinaryLogicalOperator.OR),
                                expression(variable("r"))
                        ),
                        or(and(variable("p"), variable("q")), variable("r"))
                ),
                Arguments.of(List.of(
                                expression(variable("p")),
                                operator(BinaryLogicalOperator.IMPLICATION),
                                expression(variable("q")),
                                operator(BinaryLogicalOperator.OR),
                                expression(variable("r"))
                        ),
                        implication(variable("p"), or(variable("q"), variable("r")))
                ),
                Arguments.of(List.of(
                                operator(UnaryLogicalOperator.NOT),
                                expression(variable("p")),
                                operator(BinaryLogicalOperator.OR),
                                operator(UnaryLogicalOperator.NOT),
                                expression(variable("q"))
                        ),
                        or(not(variable("p")), not(variable("q")))
                )
        );
    }
}
