package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DUP2 extends Opcode {
    public DUP2() {
        super(OpcodeType.STACK, Opcode.DUP2, 1);
    }
}
