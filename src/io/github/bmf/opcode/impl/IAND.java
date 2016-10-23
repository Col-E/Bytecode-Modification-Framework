package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IAND extends Opcode {
    public IAND() {
        super(OpcodeType.MATH, Opcode.IAND, 1);
    }
}
