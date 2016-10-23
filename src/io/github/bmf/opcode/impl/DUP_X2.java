package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DUP_X2 extends Opcode {
    public DUP_X2() {
        super(OpcodeType.STACK, Opcode.DUP_X2, 1);
    }
}
