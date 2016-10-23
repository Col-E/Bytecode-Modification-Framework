package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LXOR extends Opcode {
    public LXOR() {
        super(OpcodeType.MATH, Opcode.LXOR, 1);
    }
}
