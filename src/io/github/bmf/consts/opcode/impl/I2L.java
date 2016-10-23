package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class I2L extends Opcode {
    public I2L() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.I2L, 1);
    }
}
