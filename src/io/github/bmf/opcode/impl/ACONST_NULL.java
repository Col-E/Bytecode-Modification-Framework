package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class ACONST_NULL extends Opcode {
    public ACONST_NULL() {
        super(OpcodeType.CONST_VALUE, Opcode.ACONST_NULL, 1);
    }
}
