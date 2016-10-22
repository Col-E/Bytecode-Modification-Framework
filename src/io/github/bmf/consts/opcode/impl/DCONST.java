package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.SingleValueOpcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class DCONST extends SingleValueOpcode<Double> {
    public DCONST(int opcode, double value) {
        super(OpcodeType.CONST_VALUE, opcode, 1, value);
    }
}
