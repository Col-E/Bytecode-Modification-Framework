package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class NOP extends Opcode {
    public NOP() {
        super(OpcodeType.OTHER, Opcode.NOP, 1);
    }
}
