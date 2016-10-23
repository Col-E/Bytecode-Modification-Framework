package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DADD extends Opcode {
    public DADD() {
        super(OpcodeType.MATH, Opcode.DADD, 1);
    }
}
