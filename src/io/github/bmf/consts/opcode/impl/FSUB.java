package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class FSUB extends Opcode {
    public FSUB() {
        super(OpcodeType.MATH, Opcode.FSUB, 1);
    }
}
