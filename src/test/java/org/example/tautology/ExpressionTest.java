package org.example.tautology;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.tautology.Expression.*;

@Slf4j
public class ExpressionTest {

    @Test
    public void checkValueOfVariable() {
        Context context = new Context()
                .paramTrue("p");
        Expression expression = variable("p");
        assertThat(expression.validate(context)).isTrue();
        assertThat(expression.validate(new Context())).isFalse();
    }

    @Test
    public void checkValueOfVariableNegation() {
        Context context = new Context()
                .paramTrue("p");
        Expression expression = not(variable("p"));
        assertThat(expression.validate(context)).isFalse();
        assertThat(expression.validate(new Context())).isTrue();
    }

    @ParameterizedTest
    @MethodSource("checkAlternativeOfVariableParams")
    public void checkAlternativeOfVariable(Boolean p, Boolean q, Boolean expected) {
        Context context = new Context()
                .setParam("p", p)
                .setParam("q", q)
                ;
        Expression expression = or(variable("p"), variable("q"));
        assertThat(expression.validate(context)).isEqualTo(expected);
    }

    private static Stream<Arguments> checkAlternativeOfVariableParams() {
        return Stream.of(
                Arguments.of(true, true, true),
                Arguments.of(true, false, true),
                Arguments.of(false, true, true),
                Arguments.of(false, false, false)
        );
    }

    @ParameterizedTest
    @MethodSource("checkConjunctionOfVariableParams")
    public void checkConjunctionOfVariable(Boolean p, Boolean q, Boolean expected) {
        Context context = new Context()
                .setParam("p", p)
                .setParam("q", q)
                ;
        Expression expression = and(variable("p"), variable("q"));
        assertThat(expression.validate(context)).isEqualTo(expected);
    }

    private static Stream<Arguments> checkConjunctionOfVariableParams() {
        return Stream.of(
                Arguments.of(true, true, true),
                Arguments.of(true, false, false),
                Arguments.of(false, true, false),
                Arguments.of(false, false, false)
        );
    }

    @ParameterizedTest
    @MethodSource("checkImplicationOfVariableParams")
    public void checkImplicationOfVariable(Boolean p, Boolean q, Boolean expected) {
        Context context = new Context()
                .setParam("p", p)
                .setParam("q", q)
                ;
        Expression expression = implication(variable("p"), variable("q"));
        assertThat(expression.validate(context)).isEqualTo(expected);
    }

    private static Stream<Arguments> checkImplicationOfVariableParams() {
        return Stream.of(
                Arguments.of(true, true, true),
                Arguments.of(true, false, false),
                Arguments.of(false, true, true),
                Arguments.of(false, false, true)
        );
    }
}
