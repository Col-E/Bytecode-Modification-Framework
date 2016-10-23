package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class ISUB extends Opcode {
    public ISUB() {
        super(OpcodeType.MATH, Opcode.ISUB, 1);
    }
}
