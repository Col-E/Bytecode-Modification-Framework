package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LXOR extends Opcode {
    public LXOR() {
        super(OpcodeType.MATH, Opcode.LXOR, 1);
    }
}
