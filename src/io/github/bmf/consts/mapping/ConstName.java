package io.github.bmf.consts.mapping;

import io.github.bmf.consts.ConstUTF8;
import io.github.bmf.util.Box;

public class ConstName extends ConstUTF8 {
    public Box<String> name;

    public ConstName(Box<String> name) {
        super();
        this.name = name;
    }

    @Override
    public String getValue() {
        return name.value;
    }
}
