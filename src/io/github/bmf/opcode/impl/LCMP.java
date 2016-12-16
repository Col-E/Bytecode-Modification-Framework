package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LCMP extends Opcode {
    public LCMP() {
        super(OpcodeType.STACK, Opcode.LCMP, 1);
    }
}
