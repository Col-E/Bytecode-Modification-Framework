package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class F2L extends Opcode {
    public F2L() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.F2L, 1);
    }
}
