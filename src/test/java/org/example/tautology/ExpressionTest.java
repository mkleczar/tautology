package org.example.tautology;

import lombok.extern.slf4j.Slf4j;
import org.example.tautology.context.Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

@Slf4j
public class ExpressionTest {

    @Test
    public void checkValueOfVariable() {
        Context context =  Context.builder()
                .paramTrue("p")
                .paramFalse("q")
                .build();
        Expression expressionTrue = variable("p");
        assertThat(expressionTrue.validate(context)).isTrue();

        Expression expressionFalse = variable("q");
        assertThat(expressionFalse.validate(context)).isFalse();

        assertThat(variable("x").validate(Context.empty())).isFalse();
    }

    @Test
    public void checkValueOfConst() {
        Context context = Context.empty();
        Expression trueConst = constTrue();
        assertThat(trueConst.validate(context)).isTrue();
        Expression falseConst = constFalse();
        assertThat(falseConst.validate(context)).isFalse();
    }

    @Test
    public void checkValueOfVariableNegation() {
        Context context = Context.builder()
                .paramTrue("p").build();
        Expression expression = not(variable("p"));
        assertThat(expression.validate(context)).isFalse();
        assertThat(expression.validate(Context.empty())).isTrue();
    }

    @ParameterizedTest
    @MethodSource("checkAlternativeOfVariableParams")
    public void checkAlternativeOfVariable(Boolean p, Boolean q, Boolean expected) {
        Context context = Context.builder()
                .param("p", p)
                .param("q", q)
                .build();
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
        Context context = Context.builder()
                .param("p", p)
                .param("q", q)
                .build();
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
        Context context = Context.builder()
                .param("p", p)
                .param("q", q)
                .build();
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

    @ParameterizedTest
    @MethodSource("checkDoubleImplicationOfVariableParams")
    public void checkDoubleImplicationOfVariable(Boolean p, Boolean q, Boolean expected) {
        Context context = Context.builder()
                .param("p", p)
                .param("q", q)
                .build();
        Expression expression = doubleImplication(variable("p"), variable("q"));
        assertThat(expression.validate(context)).isEqualTo(expected);
    }

    private static Stream<Arguments> checkDoubleImplicationOfVariableParams() {
        return Stream.of(
                Arguments.of(true, true, true),
                Arguments.of(true, false, false),
                Arguments.of(false, true, false),
                Arguments.of(false, false, true)
        );
    }


}
