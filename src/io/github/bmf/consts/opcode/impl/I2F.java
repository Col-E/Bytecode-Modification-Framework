package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class I2F extends Opcode {
    public I2F() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.I2F, 1);
    }
}
