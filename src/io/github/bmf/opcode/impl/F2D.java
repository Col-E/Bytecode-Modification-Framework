package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class F2D extends Opcode {
    public F2D() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.F2D, 1);
    }
}
