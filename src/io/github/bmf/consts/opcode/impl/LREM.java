package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LREM extends Opcode {
    public LREM() {
        super(OpcodeType.MATH, Opcode.LREM, 1);
    }
}
