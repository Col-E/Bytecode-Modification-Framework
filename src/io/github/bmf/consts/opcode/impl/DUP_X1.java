package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class DUP_X1 extends Opcode {
    public DUP_X1() {
        super(OpcodeType.STACK, Opcode.DUP_X1, 1);
    }
}
