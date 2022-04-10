package org.example.tautology;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.tautology.operators.BinaryLogicalOperator.AND;
import static org.example.tautology.operators.BinaryLogicalOperator.IMPLICATION;
import static org.example.tautology.operators.BinaryLogicalOperator.OR;
import static org.example.tautology.Expression.*;
import static org.example.tautology.operators.UnaryLogicalOperator.NOT;

@Slf4j
public class ExpressionToStringTest {

    @Test
    public void variableToStringTest() {
        Expression e = variable("p");
        assertThat(e.toString()).isEqualTo("p");
    }

    @Test
    public void constToStringTest() {
        Expression e1 = constTrue();
        assertThat(e1.toString()).isEqualTo("1");
        Expression e0 = constFalse();
        assertThat(e0.toString()).isEqualTo("0");
    }

    @Test
    public void negationToStringTest() {
        Expression e = not(variable("p"));
        assertThat(e.toString()).isEqualTo(NOT.getSymbol()+"p");
    }

    @Test
    public void alternativeToStringTest() {
        Expression e = or(variable("p"), variable("q"), variable("r"));
        assertThat(e.toString()).isEqualTo(String.join(OR.getSymbol(),"(p","q","r)"));
    }

    @Test
    public void conjunctionToStringTest() {
        Expression e = and(variable("p"), variable("q"), variable("r"));
        assertThat(e.toString()).isEqualTo(String.join(AND.getSymbol(),"(p","q","r)"));
    }

    @Test
    public void implicationToStringTest() {
        Expression e = implication(variable("p"), variable("q"));
        assertThat(e.toString()).isEqualTo("(p"+IMPLICATION.getSymbol()+"q)");
    }

    @Test
    public void compoundExpressionToStringTest() {
        Expression e = implication(
                or(variable("p"), variable("q")),
                and(not(variable("p")), variable("q")));
        // log.info("Expression: {}", e);
        assertThat(e.toString()).isEqualTo("((p"+OR.getSymbol()+"q)"+IMPLICATION.getSymbol()+"("+ NOT.getSymbol()+"p"+AND.getSymbol()+"q))");
    }

}
