package io.github.bmf.util.mapping;

import java.util.HashMap;
import java.util.Map;

import io.github.bmf.util.Box;

public class Mapping {
    private final Map<String, ClassMapping> mappings = new HashMap<String, ClassMapping>();
    // TODO: Idea is to given a class find way of detecting parent
    // Unsure if it should be <Class, String> or <Class, Class>
    // Unsure of exact future usage
    private final Map<ClassMapping, ClassMapping> parents = new HashMap<ClassMapping, ClassMapping>();

    public void addMapping(ClassMapping mapping) {
        mappings.put(mapping.name.value, mapping);
    }

    public Box<String> get(String name) {
        if (mappings.containsKey(name)) { return mappings.get(name).name; }
        throw new RuntimeException("Unknown name mapping: " + name);
    }

}
