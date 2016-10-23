package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LREM extends Opcode {
    public LREM() {
        super(OpcodeType.MATH, Opcode.LREM, 1);
    }
}
