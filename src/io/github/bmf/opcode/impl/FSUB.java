package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class FSUB extends Opcode {
    public FSUB() {
        super(OpcodeType.MATH, Opcode.FSUB, 1);
    }
}
