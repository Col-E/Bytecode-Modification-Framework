package io.github.bmf.type.descriptors;

import io.github.bmf.type.Type;

public class VariableDescriptor extends MemberDescriptor {
    public Type fieldType;

    public VariableDescriptor(Type type) {
        super(type.toDesc());
        fieldType = type;
    }

    @Override
    public String toDesc() {
        return fieldType.toDesc();
    }
}