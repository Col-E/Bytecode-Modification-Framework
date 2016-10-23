package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DMUL extends Opcode {
    public DMUL() {
        super(OpcodeType.MATH, Opcode.DMUL, 1);
    }
}
