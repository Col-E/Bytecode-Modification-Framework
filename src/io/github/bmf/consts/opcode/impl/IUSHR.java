package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class IUSHR extends Opcode {
    public IUSHR() {
        super(OpcodeType.MATH, Opcode.IUSHR, 1);
    }
}
