package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class ISHL extends Opcode {
    public ISHL() {
        super(OpcodeType.MATH, Opcode.ISHL, 1);
    }
}
