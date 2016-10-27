package io.github.bmf.util.descriptors;

import io.github.bmf.type.Type;

import java.util.ArrayList;

public class MethodDescriptor extends MemberDescriptor {

    public final ArrayList<Type> parameters;
    public Type returnType;

    public MethodDescriptor(ArrayList<Type> parameters, Type returnType) {
        this.parameters = parameters;
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Type parameter : parameters) {
            sb.append(parameter.toString());
        }
        sb.append(")");
        sb.append(returnType.toString());
        return sb.toString();
    }
}
