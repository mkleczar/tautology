package org.example.tautology;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private final Map<String, Boolean> params;

    private Context(Map<String, Boolean> params) {
        this.params = params;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Context empty() {
        return new Context(new HashMap<>());
    }

    public Boolean check(String name) {
        return params.getOrDefault(name, false);
    }

    public static class Builder {
        private Map<String, Boolean> params = new HashMap<>();

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
    }
}
