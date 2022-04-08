package org.example.tautology;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.tautology.BinaryLogicalOperator.AND;
import static org.example.tautology.BinaryLogicalOperator.IMPLICATION;
import static org.example.tautology.BinaryLogicalOperator.OR;
import static org.example.tautology.Expression.and;
import static org.example.tautology.Expression.implication;
import static org.example.tautology.Expression.not;
import static org.example.tautology.Expression.or;
import static org.example.tautology.Expression.variable;
import static org.example.tautology.UnaryLogicalOperator.NOT;

@Slf4j
public class ExpressionToStringTest {

    @Test
    public void variableToStringTest() {
        Expression e = variable("p");
        assertThat(e.toString()).isEqualTo("p");
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
        log.info("Expression: {}", e);
        assertThat(e.toString()).isEqualTo("((p"+OR.getSymbol()+"q)"+IMPLICATION.getSymbol()+"("+ NOT.getSymbol()+"p"+AND.getSymbol()+"q))");
    }

}
