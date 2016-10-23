package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class DUP extends Opcode {
    public DUP() {
        super(OpcodeType.STACK, Opcode.DUP, 1);
    }
}
