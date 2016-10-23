package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class IXOR extends Opcode {
    public IXOR() {
        super(OpcodeType.MATH, Opcode.IXOR, 1);
    }
}
