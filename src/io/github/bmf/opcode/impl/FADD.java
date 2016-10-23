package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class FADD extends Opcode {
    public FADD() {
        super(OpcodeType.MATH, Opcode.FADD, 1);
    }
}
