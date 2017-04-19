package io.github.bmf.consts.mapping;

import io.github.bmf.consts.ConstUTF8;
import io.github.bmf.util.Box;

public class ConstName extends ConstUTF8 {
    private Box<String> name;

    public ConstName(String name) {
        this(new Box<String>(name));
    }

    public ConstName(Box<String> name) {
        super();
        this.name = name;
    }

    @Override
    public String getValue() {
        return name.getValue();
    }

    @Override
    public void setValue(String value) {
        name.setValue(value);
    }
}
