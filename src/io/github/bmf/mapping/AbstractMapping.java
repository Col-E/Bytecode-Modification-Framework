package io.github.bmf.mapping;

import io.github.bmf.util.Box;

public class AbstractMapping {
    public final Box<String> name;

    public AbstractMapping(String name) {
        this(new Box<String>(name));
    }

    public AbstractMapping(Box<String> name) {
        this.name = name;
    }
}
