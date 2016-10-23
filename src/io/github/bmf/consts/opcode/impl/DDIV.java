package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class DDIV extends Opcode {
    public DDIV() {
        super(OpcodeType.MATH, Opcode.DDIV, 1);
    }
}
