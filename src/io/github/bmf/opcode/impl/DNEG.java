package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DNEG extends Opcode {
    public DNEG() {
        super(OpcodeType.MATH, Opcode.DNEG, 1);
    }
}
