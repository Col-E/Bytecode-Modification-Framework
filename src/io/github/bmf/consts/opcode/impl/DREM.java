package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class DREM extends Opcode {
    public DREM() {
        super(OpcodeType.MATH, Opcode.DREM, 1);
    }
}
