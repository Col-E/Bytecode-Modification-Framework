package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class FCMPG extends Opcode {
    public FCMPG() {
        super(OpcodeType.STACK, Opcode.FCMPG, 1);
    }
}
