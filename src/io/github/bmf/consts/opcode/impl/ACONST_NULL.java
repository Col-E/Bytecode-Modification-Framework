package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class ACONST_NULL extends Opcode {
    public ACONST_NULL() {
        super(OpcodeType.CONST_VALUE, Opcode.ACONST_NULL, 1);
    }
}
