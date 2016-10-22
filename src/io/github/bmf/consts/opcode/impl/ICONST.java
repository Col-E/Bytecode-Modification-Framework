package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.ConstOpcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class ICONST extends ConstOpcode<Integer> {
    public ICONST(int opcode, int value) {
        super(OpcodeType.CONST_VALUE, opcode, 1, value);
    }
}
