package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class L2I extends Opcode {
    public L2I() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.L2I, 1);
    }
}
