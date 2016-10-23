package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class INEG extends Opcode {
    public INEG() {
        super(OpcodeType.MATH, Opcode.INEG, 1);
    }
}
