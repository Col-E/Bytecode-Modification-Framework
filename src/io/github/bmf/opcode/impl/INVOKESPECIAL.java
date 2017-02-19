package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractMethodOpcode;
import io.github.bmf.opcode.Opcode;

public class INVOKESPECIAL extends AbstractMethodOpcode {
    public INVOKESPECIAL(int methodIndex) {
        super(Opcode.INVOKESPECIAL, 5, methodIndex);
    }
}
