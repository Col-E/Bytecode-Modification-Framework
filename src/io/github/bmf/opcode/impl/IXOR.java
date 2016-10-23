package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IXOR extends Opcode {
    public IXOR() {
        super(OpcodeType.MATH, Opcode.IXOR, 1);
    }
}
