package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LDIV extends Opcode {
    public LDIV() {
        super(OpcodeType.MATH, Opcode.LDIV, 1);
    }
}
