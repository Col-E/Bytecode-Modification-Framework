package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class FMUL extends Opcode {
    public FMUL() {
        super(OpcodeType.MATH, Opcode.FMUL, 1);
    }
}
