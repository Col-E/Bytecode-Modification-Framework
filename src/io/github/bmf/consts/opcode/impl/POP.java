package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class POP extends Opcode {
    public POP() {
        super(OpcodeType.STACK, Opcode.POP, 1);
    }
}
