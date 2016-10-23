package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class DMUL extends Opcode {
    public DMUL() {
        super(OpcodeType.MATH, Opcode.DMUL, 1);
    }
}
