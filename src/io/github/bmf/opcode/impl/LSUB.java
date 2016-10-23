package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LSUB extends Opcode {
    public LSUB() {
        super(OpcodeType.MATH, Opcode.LSUB, 1);
    }
}
