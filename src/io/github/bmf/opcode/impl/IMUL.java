package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IMUL extends Opcode {
    public IMUL() {
        super(OpcodeType.MATH, Opcode.IMUL, 1);
    }
}
