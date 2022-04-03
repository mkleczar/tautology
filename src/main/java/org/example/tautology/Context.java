package org.example.tautology;

import java.util.HashSet;
import java.util.Set;

public class Context {

    private final Set<String> params = new HashSet<>();

    // TODO: move this to builder
    public Context setParam(String name, Boolean value) {
        if (value) {
            params.add(name);
        }
        return this;
    }

    // TODO: move this to builder
    public Context paramTrue(String name) {
        params.add(name);
        return this;
    }

    public Boolean check(String name) {
        return params.contains(name);
    }
}
