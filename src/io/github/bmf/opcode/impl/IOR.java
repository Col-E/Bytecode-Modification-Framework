package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IOR extends Opcode {
    public IOR() {
        super(OpcodeType.MATH, Opcode.IOR, 1);
    }
}
