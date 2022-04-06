package org.example.tautology;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.tautology.Expression.*;

public class ExpressionToStringTest {

    @Test
    public void variableToStringTest() {
        Expression e = variable("p");
        assertThat(e.toString()).isEqualTo("p");
    }

    @Test
    public void negationToStringTest() {
        Expression e = not(variable("p"));
        assertThat(e.toString()).isEqualTo(NEGATION_STR+"p");
    }

    @Test
    public void alternativeToStringTest() {
        Expression e = or(variable("p"), variable("q"), variable("r"));
        assertThat(e.toString()).isEqualTo(String.join(ALTERNATIVE_STR,"(p","q","r)"));
    }

    @Test
    public void conjunctionToStringTest() {
        Expression e = and(variable("p"), variable("q"), variable("r"));
        assertThat(e.toString()).isEqualTo(String.join(CONJUNCTION_STR,"(p","q","r)"));
    }

    @Test
    public void implicationToStringTest() {
        Expression e = implication(variable("p"), variable("q"));
        assertThat(e.toString()).isEqualTo("(p"+IMPLICATION_STR+"q)");
    }

    @Test
    public void compoundExpressionToStringTest() {
        Expression e = implication(
                or(variable("p"), variable("q")),
                and(not(variable("p")), variable("q")));
        assertThat(e.toString()).isEqualTo("((p"+ALTERNATIVE_STR+"q)"+IMPLICATION_STR+"("+ NEGATION_STR+"p"+CONJUNCTION_STR+"q))");
    }

}
