package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class FDIV extends Opcode {
    public FDIV() {
        super(OpcodeType.MATH, Opcode.FDIV, 1);
    }
}
