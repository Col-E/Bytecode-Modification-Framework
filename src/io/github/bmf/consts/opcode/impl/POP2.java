package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class POP2 extends Opcode {
    public POP2() {
        super(OpcodeType.STACK, Opcode.POP2, 1);
    }
}
