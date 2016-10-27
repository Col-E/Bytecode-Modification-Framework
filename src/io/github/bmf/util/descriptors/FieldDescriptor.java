package io.github.bmf.util.descriptors;

import io.github.bmf.type.Type;

public class FieldDescriptor  extends MemberDescriptor {

    public Type fieldType;

    public FieldDescriptor(Type type) {
        fieldType = type;
    }

    @Override
    public String toString() {
        return fieldType.toString();
    }
}