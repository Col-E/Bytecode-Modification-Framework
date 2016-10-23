package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class IAND extends Opcode {
    public IAND() {
        super(OpcodeType.MATH, Opcode.IAND, 1);
    }
}
