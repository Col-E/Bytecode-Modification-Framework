package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;
import io.github.bmf.opcode.SingleValueOpcode;

public class DCONST extends SingleValueOpcode<Double> {
    public DCONST(double value) {
        super(OpcodeType.CONST_VALUE, opFromValue((int) Math.round(value)), 1, value);
    }

    private static int opFromValue(int value) {
        if (value == 0) return Opcode.DCONST_0;
        else return Opcode.DCONST_1;
    }
}
