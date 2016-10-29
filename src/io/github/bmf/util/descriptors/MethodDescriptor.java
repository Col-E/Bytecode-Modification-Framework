package io.github.bmf.util.descriptors;

import io.github.bmf.type.Type;

import java.util.List;

public class MethodDescriptor extends MemberDescriptor {

    public final List<Type> parameters;
    public Type returnType;

    public MethodDescriptor(String desc, List<Type> parameters, Type returnType) {
        super(desc);
        this.parameters = parameters;
        this.returnType = returnType;
    }

   
    @Override
    public String toDesc() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Type parameter : parameters) {
            sb.append(parameter.toDesc());
        }
        sb.append(")");
        sb.append(returnType.toDesc());
        return sb.toString();
    }

}
