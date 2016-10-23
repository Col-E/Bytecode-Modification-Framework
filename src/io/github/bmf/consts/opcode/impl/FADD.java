package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class FADD extends Opcode {
    public FADD() {
        super(OpcodeType.MATH, Opcode.FADD, 1);
    }
}
