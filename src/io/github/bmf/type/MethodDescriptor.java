package io.github.bmf.type;

import java.util.ArrayList;

public class MethodDescriptor {

    public final ArrayList<Type> parameters;
    public Type returnType;

    public MethodDescriptor(ArrayList<Type> parameters, Type returnType) {
        this.parameters = parameters;
        this.returnType = returnType;
    }

}
