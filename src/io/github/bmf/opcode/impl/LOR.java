package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LOR extends Opcode {
    public LOR() {
        super(OpcodeType.MATH, Opcode.LOR, 1);
    }
}
