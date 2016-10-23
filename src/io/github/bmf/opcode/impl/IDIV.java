package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IDIV extends Opcode {
    public IDIV() {
        super(OpcodeType.MATH, Opcode.IDIV, 1);
    }
}
