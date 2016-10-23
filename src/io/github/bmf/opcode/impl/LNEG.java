package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LNEG extends Opcode {
    public LNEG() {
        super(OpcodeType.MATH, Opcode.LNEG, 1);
    }
}
