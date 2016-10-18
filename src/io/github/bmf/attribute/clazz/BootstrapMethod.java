package io.github.bmf.attribute.clazz;

import io.github.bmf.util.Measurable;

import java.util.ArrayList;
import java.util.List;

public class BootstrapMethod implements Measurable {
    public int methodReference;
    public List<Integer> arguments;

    public BootstrapMethod(int methodReference, int argCount) {
        this.methodReference = methodReference;
        this.arguments = new ArrayList<Integer>(argCount);
    }

    public void addArgument(int arg) {
        arguments.add(arg);
    }

    @Override
    public int getLength() {
        // u2: method_ref
        // u2: num_args
        // u2[]: args
        return 4 + 2 * arguments.size();
    }

}
