package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class D2L extends Opcode {
    public D2L() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.D2L, 1);
    }
}
