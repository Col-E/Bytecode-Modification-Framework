package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class L2F extends Opcode {
    public L2F() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.L2F, 1);
    }
}
