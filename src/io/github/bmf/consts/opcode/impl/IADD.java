package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class IADD extends Opcode {
    public IADD() {
        super(OpcodeType.MATH, Opcode.IADD, 1);
    }
}
