package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DSUB extends Opcode {
    public DSUB() {
        super(OpcodeType.MATH, Opcode.DSUB, 1);
    }
}
