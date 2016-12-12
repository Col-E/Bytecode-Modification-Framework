package io.github.bmf.type.descriptors;

import io.github.bmf.type.Type;

public class VariableDescriptor extends MemberDescriptor {
    public Type type;

    public VariableDescriptor(Type type) {
        super(type.toDesc());
        this.type = type;
    }

    @Override
    public String toDesc() {
        return type.toDesc();
    }
}