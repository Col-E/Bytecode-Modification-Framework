package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.SingleValueOpcode;
import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LCONST extends SingleValueOpcode<Long> {
    public LCONST(long value) {
        super(OpcodeType.CONST_VALUE, opFromValue((int) value), 1, value);
    }

    private static int opFromValue(int value) {
        if (value == 0) return Opcode.LCONST_0;
        else return Opcode.LCONST_1;
    }
}
