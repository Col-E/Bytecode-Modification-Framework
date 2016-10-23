package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class DADD extends Opcode {
    public DADD() {
        super(OpcodeType.MATH, Opcode.DADD, 1);
    }
}
