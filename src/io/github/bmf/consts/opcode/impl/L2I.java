package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class L2I extends Opcode {
    public L2I() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.L2I, 1);
    }
}
