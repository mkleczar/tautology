package org.example.tautology;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.tautology.operators.BinaryLogicalOperator.*;
import static org.example.tautology.operators.UnaryLogicalOperator.NOT;

public class LogicOperatorTest {

    @Test
    public void checkPriority() {
        assertThat(NOT.getPriority() > AND.getPriority()).isTrue();
        assertThat(AND.getPriority() > OR.getPriority()).isTrue();
        assertThat(OR.getPriority() > IMPLICATION.getPriority()).isTrue();
        assertThat(IMPLICATION.getPriority() > DOUBLE_IMPLICATION.getPriority()).isTrue();
    }
}
