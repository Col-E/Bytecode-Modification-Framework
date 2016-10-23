package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LSHL extends Opcode {
    public LSHL() {
        super(OpcodeType.MATH, Opcode.LSHL, 1);
    }
}
