package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class F2I extends Opcode {
    public F2I() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.F2I, 1);
    }
}
