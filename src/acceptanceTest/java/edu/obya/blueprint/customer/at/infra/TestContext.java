package edu.obya.blueprint.customer.at.infra;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"unchecked"})
public class TestContext extends HashMap<String, Object> {

    private static final TestContext instance = new TestContext();

    public static <T> void set(String id, T value) {
        instance.put(id, value);
    }

    public static <T> T at(String id) {
        if (!instance.containsKey(id)) {
            throw new IllegalArgumentException("no context object registered with " + id);
        }
        return (T) instance.get(id);
    }

    public static <T> T atAs(String id, Class<T> type) {
        return at(id);
    }

    public static <T> List<T> atAsList(String id, Class<T> type) {
        return at(id);
    }

    public static <T> Optional<T> atOpt(String id) {
        return Optional.ofNullable((T) instance.get(id));
    }

    public static <T> T atOrDefault(String id, T defaultValue) {
        return Optional.ofNullable((T) instance.get(id)).orElse(defaultValue);
    }

    public static void reset() {
        instance.clear();
    }
}
