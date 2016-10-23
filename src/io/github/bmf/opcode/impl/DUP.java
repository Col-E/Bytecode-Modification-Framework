package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DUP extends Opcode {
    public DUP() {
        super(OpcodeType.STACK, Opcode.DUP, 1);
    }
}
