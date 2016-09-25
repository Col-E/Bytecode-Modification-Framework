package io.github.bmf.attribute.method;

import io.github.bmf.util.IMeasurable;

public class MethodException implements IMeasurable {
    /**
     * Opcode indices in the method.
     */
    public int start, end, handler;
    /**
     * Index in the constant pool of the type of exeption handled.
     */
    public int type;

    @Override
    public int getLength() {
        // u2: start_pc
        // u2: end_pc
        // u2: handler_pc
        // u2: catch_type
        return 8;
    }
}
