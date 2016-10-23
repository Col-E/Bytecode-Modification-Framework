package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class D2L extends Opcode {
    public D2L() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.D2L, 1);
    }
}
