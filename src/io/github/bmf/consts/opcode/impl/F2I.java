package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class F2I extends Opcode {
    public F2I() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.F2I, 1);
    }
}
