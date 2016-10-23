package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DUP2_X2 extends Opcode {
    public DUP2_X2() {
        super(OpcodeType.STACK, Opcode.DUP2_X2, 1);
    }
}
