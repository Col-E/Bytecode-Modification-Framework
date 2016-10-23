package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class POP2 extends Opcode {
    public POP2() {
        super(OpcodeType.STACK, Opcode.POP2, 1);
    }
}
