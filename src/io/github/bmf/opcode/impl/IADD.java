package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IADD extends Opcode {
    public IADD() {
        super(OpcodeType.MATH, Opcode.IADD, 1);
    }
}
