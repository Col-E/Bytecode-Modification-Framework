package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LDIV extends Opcode {
    public LDIV() {
        super(OpcodeType.MATH, Opcode.LDIV, 1);
    }
}
