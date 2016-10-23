package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class DNEG extends Opcode {
    public DNEG() {
        super(OpcodeType.MATH, Opcode.DNEG, 1);
    }
}
