package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LMUL extends Opcode {
    public LMUL() {
        super(OpcodeType.MATH, Opcode.LMUL, 1);
    }
}
