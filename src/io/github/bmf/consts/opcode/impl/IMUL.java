package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class IMUL extends Opcode {
    public IMUL() {
        super(OpcodeType.MATH, Opcode.IMUL, 1);
    }
}
