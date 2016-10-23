package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class L2D extends Opcode {
    public L2D() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.L2D, 1);
    }
}
