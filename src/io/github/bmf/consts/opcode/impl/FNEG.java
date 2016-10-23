package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class FNEG extends Opcode {
    public FNEG() {
        super(OpcodeType.MATH, Opcode.FNEG, 1);
    }
}
