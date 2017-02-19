package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractMethodOpcode;
import io.github.bmf.opcode.Opcode;

public class INVOKEVIRTUAL extends AbstractMethodOpcode {
    public INVOKEVIRTUAL(int methodIndex) {
        super(Opcode.INVOKEVIRTUAL, 5, methodIndex);
    }
}
