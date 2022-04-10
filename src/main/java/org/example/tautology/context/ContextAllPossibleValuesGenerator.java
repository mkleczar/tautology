package org.example.tautology.context;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ContextAllPossibleValuesGenerator {

    private boolean first = true;
    private final SortedMap<String, Boolean> contextMap;

    public ContextAllPossibleValuesGenerator(Set<String> parameters) {
        Map<String, Boolean> map = parameters.stream()
                .collect(Collectors.toMap(Function.identity(), str -> false));
        this.contextMap = new TreeMap<>(map);
    }

    public Stream<Context> stream() {
        return Stream.iterate(contextMap,
                m -> first || m.values().stream().anyMatch(v -> v),
                m -> {
                    first = false;
                    for (Map.Entry<String, Boolean> entry : m.entrySet()) {
                        if (entry.getValue()) {
                            entry.setValue(false);
                        } else {
                            entry.setValue(true);
                            break;
                        }
                    }
                    return m;
                })
                .map(Context::new);
    }
}
