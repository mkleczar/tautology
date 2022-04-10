package org.example.tautology;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.tautology.Expression.*;

public class ExpressionIsVariableTest {
    @Test
    public void isVariableTest() {
        assertThat(variable("p").isVariable()).isTrue();
        assertThat(constTrue().isVariable()).isFalse();
        assertThat(not(variable("q")).isVariable()).isFalse();
        assertThat(or(variable("p"), variable("q")).isVariable()).isFalse();
    }
}
