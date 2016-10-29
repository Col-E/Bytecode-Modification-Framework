package io.github.bmf.util.descriptors;

import io.github.bmf.type.Type;

public class FieldDescriptor  extends MemberDescriptor {
    public Type fieldType;

    public FieldDescriptor(Type type) {
        super(type.toDesc());
        fieldType = type;
    }

    @Override
    public String toDesc() {
        return fieldType.toString();
    }
}