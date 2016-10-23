package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class FNEG extends Opcode {
    public FNEG() {
        super(OpcodeType.MATH, Opcode.FNEG, 1);
    }
}
