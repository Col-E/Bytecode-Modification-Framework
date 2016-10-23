package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class IDIV extends Opcode {
    public IDIV() {
        super(OpcodeType.MATH, Opcode.IDIV, 1);
    }
}
