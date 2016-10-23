package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IREM extends Opcode {
    public IREM() {
        super(OpcodeType.MATH, Opcode.IREM, 1);
    }
}
