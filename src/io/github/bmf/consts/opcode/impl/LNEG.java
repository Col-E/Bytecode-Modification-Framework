package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LNEG extends Opcode {
    public LNEG() {
        super(OpcodeType.MATH, Opcode.LNEG, 1);
    }
}
