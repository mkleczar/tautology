package org.example.tautology;

import lombok.extern.slf4j.Slf4j;
import org.example.tautology.context.Context;
import org.example.tautology.context.ContextAllPossibleValuesGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ContextTest {

    @Test
    public void contextStreamTest() {
        Set<String> params = Set.of("p", "q", "r");
        ContextAllPossibleValuesGenerator it = new ContextAllPossibleValuesGenerator(params);
        assertThat(it.stream().count()).isEqualTo(8);
    }

    @Test
    public void contextStreamWithEmptyParams() {
        Set<String> params = Set.of();
        ContextAllPossibleValuesGenerator it = new ContextAllPossibleValuesGenerator(params);
        List<Context> list = it.stream().collect(Collectors.toList());
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).isEmpty()).isTrue();
    }
}
