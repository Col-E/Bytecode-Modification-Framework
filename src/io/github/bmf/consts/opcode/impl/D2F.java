package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class D2F extends Opcode {
    public D2F() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.D2F, 1);
    }
}
