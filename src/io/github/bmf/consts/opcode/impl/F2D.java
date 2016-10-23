package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class F2D extends Opcode {
    public F2D() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.F2D, 1);
    }
}
