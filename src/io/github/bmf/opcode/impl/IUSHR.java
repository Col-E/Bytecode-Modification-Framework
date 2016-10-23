package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IUSHR extends Opcode {
    public IUSHR() {
        super(OpcodeType.MATH, Opcode.IUSHR, 1);
    }
}
