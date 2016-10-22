package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.SingleValueOpcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class FCONST extends SingleValueOpcode<Float> {
    public FCONST(int opcode, float value) {
        super(OpcodeType.CONST_VALUE, opcode, 1, value);
    }
}
