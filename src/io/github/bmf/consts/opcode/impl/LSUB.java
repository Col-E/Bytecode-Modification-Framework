package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LSUB extends Opcode {
    public LSUB() {
        super(OpcodeType.MATH, Opcode.LSUB, 1);
    }
}
