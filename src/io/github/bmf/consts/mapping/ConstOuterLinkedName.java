package io.github.bmf.consts.mapping;

import io.github.bmf.ClassNode;
import io.github.bmf.util.Box;

public class ConstOuterLinkedName extends ConstName {
    private Box<String> name;
    public ClassNode outer;

    public ConstOuterLinkedName(ClassNode outer, Box<String> name) {
        super(name);
        this.name = name;
    }

    @Override
    public String getValue() {
        return name.getValue();
    }

    @Override
    public void setValue(String value) {
        int nameIndex = value.lastIndexOf("$");
        if (nameIndex == -1) {
            name.setValue(outer.getName() + "$" + value);
        } else {
            name.setValue(outer.getName() + "$" + value.substring(nameIndex + 1));
        }
    }
}
