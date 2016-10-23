package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LSHR extends Opcode {
    public LSHR() {
        super(OpcodeType.MATH, Opcode.LSHR, 1);
    }
}
