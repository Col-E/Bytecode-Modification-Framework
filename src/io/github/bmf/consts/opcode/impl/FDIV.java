package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class FDIV extends Opcode {
    public FDIV() {
        super(OpcodeType.MATH, Opcode.FDIV, 1);
    }
}
