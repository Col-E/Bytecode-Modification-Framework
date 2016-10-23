package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LSHL extends Opcode {
    public LSHL() {
        super(OpcodeType.MATH, Opcode.LSHL, 1);
    }
}
