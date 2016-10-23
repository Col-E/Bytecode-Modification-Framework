package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class ISUB extends Opcode {
    public ISUB() {
        super(OpcodeType.MATH, Opcode.ISUB, 1);
    }
}
