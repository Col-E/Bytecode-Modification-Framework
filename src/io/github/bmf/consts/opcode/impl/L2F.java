package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class L2F extends Opcode {
    public L2F() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.L2F, 1);
    }
}
