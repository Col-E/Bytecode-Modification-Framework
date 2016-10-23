package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LMUL extends Opcode {
    public LMUL() {
        super(OpcodeType.MATH, Opcode.LMUL, 1);
    }
}
