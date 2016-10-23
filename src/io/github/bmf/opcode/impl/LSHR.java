package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LSHR extends Opcode {
    public LSHR() {
        super(OpcodeType.MATH, Opcode.LSHR, 1);
    }
}
