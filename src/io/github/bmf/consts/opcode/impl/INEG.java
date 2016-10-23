package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class INEG extends Opcode {
    public INEG() {
        super(OpcodeType.MATH, Opcode.INEG, 1);
    }
}
