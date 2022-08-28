package org.example.tautology.context;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Context {

    private final SortedMap<String, Boolean> params;

    Context(SortedMap<String, Boolean> params) {
        this.params = params;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Context empty() {
        return new Context(new TreeMap<>());
    }

    public boolean isEmpty() {
        return params.isEmpty();
    }

    public Boolean check(String name) {
        return params.getOrDefault(name, false);
    }

    @Override
    public String toString() {
        return "Context{" +
                "params=" + params +
                '}';
    }

    public static class Builder {
        private SortedMap<String, Boolean> params = new TreeMap<>();

        public Context build() {
            var tmp = params;
            params = null;
            return new Context(tmp);
        }

        public Builder param(String name, Boolean value) {
            params.put(name, value);
            return this;
        }

        public Builder paramTrue(String name) {
            return param(name, true);
        }
        public Builder paramFalse(String name) {
            return param(name, false);
        }

        public Builder merge(Builder b) {
            params.putAll(b.params);
            return this;
        }
    }
}
