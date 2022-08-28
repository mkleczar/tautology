package org.example.tautology;

import org.example.tautology.context.Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.tautology.Expression.and;
import static org.example.tautology.Expression.constFalse;
import static org.example.tautology.Expression.constTrue;
import static org.example.tautology.Expression.doubleImplication;
import static org.example.tautology.Expression.implication;
import static org.example.tautology.Expression.not;
import static org.example.tautology.Expression.or;
import static org.example.tautology.Expression.variable;

public class ExpressionHelperTest {

    @ParameterizedTest
    @MethodSource("collectingVariablesData")
    public void testCollectingVariables(Expression expression, Set<String> expected) {
        assertThat(ExpressionHelper.collectVariables(expression)).containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> collectingVariablesData() {
        return Stream.of(
                Arguments.of(variable("p"), Set.of("p")),
                Arguments.of(and(variable("p"),variable("q")), Set.of("p", "q")),
                Arguments.of(or(variable("p"),variable("q"), variable("r")), Set.of("p", "q", "r")),
                Arguments.of(implication(variable("p"),variable("p")), Set.of("p")),
                Arguments.of(not(or(variable("p"),not(variable("q")))), Set.of("p","q")),
                Arguments.of(or(constFalse(), constTrue()), Set.of()),
                Arguments.of(doubleImplication(variable("p"),not(variable("q"))), Set.of("p", "q"))
        );
    }

    @ParameterizedTest
    @MethodSource("isTautologyData")
    public void testIsTautology(Expression expression, boolean isTautology) {
        assertThat(ExpressionHelper.isTautology(expression)).isEqualTo(isTautology);
    }

    private static Stream<Arguments> isTautologyData() {
        return Stream.of(
                Arguments.of(or(variable("p"), not(variable("p"))), true),
                Arguments.of(and(variable("p"), not(variable("p"))), false),
                Arguments.of(not(and(variable("p"), not(variable("p")))), true),
                Arguments.of(doubleImplication(variable("p"), variable("p")), true),
                Arguments.of(implication(constFalse(), variable("p")), true),
                Arguments.of(constFalse(), false),
                Arguments.of(not(constFalse()), true),
                Arguments.of(constTrue(), true),
                Arguments.of(
                        doubleImplication(
                                and(not(variable("p")), not(variable("q"))),
                                not(or(variable("p"), variable("q")))),
                         true)
        );
    }

    @ParameterizedTest
    @MethodSource("popularTautologyData")
    public void popularTautologyTest(Expression expression) {
        assertThat(ExpressionHelper.isTautology(expression)).isEqualTo(true);
    }

    private static Stream<Arguments> popularTautologyData() {
        Expression modusTollendoTollens = implication(and(implication(variable("p"), variable("q")), not(variable("q"))), not(variable("p")));
        Expression modusPonendoPonens = implication(and(implication(variable("p"), variable("q")), variable("p")), variable("q"));
        Expression modusTollendoPonens = implication(and(or(variable("p"), variable("q")), not(variable("p"))), variable("q"));
        Expression modusPonendoTollens = implication(and(not(and(variable("p"), variable("q"))), variable("p")), not(variable("q")));
        return Stream.of(
                Arguments.of(modusTollendoTollens),
                Arguments.of(modusPonendoPonens),
                Arguments.of(modusTollendoPonens),
                Arguments.of(modusPonendoTollens)
        );
    }

    @ParameterizedTest
    @MethodSource("validationData")
    public void validationTest(String expressionStr, Context context, boolean expected) {
        Expression expression = Scanner.scan(expressionStr);
        boolean result = ExpressionHelper.validate(expression, context);
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> validationData() {
        return Stream.of(
                Arguments.of("~p|q", Context.builder().paramTrue("p").paramTrue("q").build(), true),
                Arguments.of("~p|q", Context.builder().paramFalse("p").paramTrue("q").build(), true),
                Arguments.of("~p|q", Context.builder().paramTrue("p").paramFalse("q").build(), false),
                Arguments.of("~p|q", Context.builder().paramFalse("p").paramFalse("q").build(), true),

                Arguments.of("p=>q", Context.builder().paramTrue("p").paramTrue("q").build(), true),
                Arguments.of("p=>q", Context.builder().paramFalse("p").paramTrue("q").build(), true),
                Arguments.of("p=>q", Context.builder().paramTrue("p").paramFalse("q").build(), false),
                Arguments.of("p=>q", Context.builder().paramFalse("p").paramFalse("q").build(), true),

                Arguments.of("(p|q)&~p", Context.builder().paramTrue("p").paramTrue("q").build(), false),
                Arguments.of("(p|q)&~p", Context.builder().paramFalse("p").paramTrue("q").build(), true),
                Arguments.of("(p|q)&~p", Context.builder().paramTrue("p").paramFalse("q").build(), false),
                Arguments.of("(p|q)&~p", Context.builder().paramFalse("p").paramFalse("q").build(), false)
        );
    }
}
