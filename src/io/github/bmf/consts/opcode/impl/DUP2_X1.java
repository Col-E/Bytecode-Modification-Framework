package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class DUP2_X1 extends Opcode {
    public DUP2_X1() {
        super(OpcodeType.STACK, Opcode.DUP2_X1, 1);
    }
}
