package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class DSUB extends Opcode {
    public DSUB() {
        super(OpcodeType.MATH, Opcode.DSUB, 1);
    }
}
