package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class D2I extends Opcode {
    public D2I() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.D2I, 1);
    }
}
