package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LADD extends Opcode {
    public LADD() {
        super(OpcodeType.MATH, Opcode.LADD, 1);
    }
}
