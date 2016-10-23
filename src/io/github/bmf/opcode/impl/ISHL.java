package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class ISHL extends Opcode {
    public ISHL() {
        super(OpcodeType.MATH, Opcode.ISHL, 1);
    }
}
