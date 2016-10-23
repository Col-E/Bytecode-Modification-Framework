package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class L2D extends Opcode {
    public L2D() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.L2D, 1);
    }
}
