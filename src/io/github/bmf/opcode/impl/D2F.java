package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class D2F extends Opcode {
    public D2F() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.D2F, 1);
    }
}
