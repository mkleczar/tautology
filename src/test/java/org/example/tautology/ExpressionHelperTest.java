package org.example.tautology;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
}
