package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DDIV extends Opcode {
    public DDIV() {
        super(OpcodeType.MATH, Opcode.DDIV, 1);
    }
}
