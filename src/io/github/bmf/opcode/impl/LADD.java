package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LADD extends Opcode {
    public LADD() {
        super(OpcodeType.MATH, Opcode.LADD, 1);
    }
}
