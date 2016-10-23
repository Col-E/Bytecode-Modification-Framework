package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class IREM extends Opcode {
    public IREM() {
        super(OpcodeType.MATH, Opcode.IREM, 1);
    }
}
