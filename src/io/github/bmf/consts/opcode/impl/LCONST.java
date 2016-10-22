package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.SingleValueOpcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LCONST extends SingleValueOpcode<Long> {
    public LCONST(int opcode, long value) {
        super(OpcodeType.CONST_VALUE, opcode, 1, value);
    }
}
