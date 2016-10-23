package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DREM extends Opcode {
    public DREM() {
        super(OpcodeType.MATH, Opcode.DREM, 1);
    }
}
