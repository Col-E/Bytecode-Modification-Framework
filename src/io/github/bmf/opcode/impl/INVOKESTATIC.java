package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractMethodOpcode;
import io.github.bmf.opcode.Opcode;

public class INVOKESTATIC extends AbstractMethodOpcode {
    public INVOKESTATIC(int methodIndex) {
        super(Opcode.INVOKESTATIC, 5, methodIndex);
    }
}
