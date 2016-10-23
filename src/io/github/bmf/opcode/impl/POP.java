package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class POP extends Opcode {
    public POP() {
        super(OpcodeType.STACK, Opcode.POP, 1);
    }
}
