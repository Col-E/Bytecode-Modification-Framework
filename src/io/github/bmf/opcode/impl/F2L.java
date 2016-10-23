package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class F2L extends Opcode {
    public F2L() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.F2L, 1);
    }
}
