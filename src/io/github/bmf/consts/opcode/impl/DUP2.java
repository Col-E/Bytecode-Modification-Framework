package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class DUP2 extends Opcode {
    public DUP2() {
        super(OpcodeType.STACK, Opcode.DUP2, 1);
    }
}
