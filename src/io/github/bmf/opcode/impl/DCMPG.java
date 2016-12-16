package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DCMPG extends Opcode {
    public DCMPG() {
        super(OpcodeType.STACK, Opcode.DCMPG, 1);
    }
}
