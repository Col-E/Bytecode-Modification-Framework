package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class I2C extends Opcode {
    public I2C() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.I2C, 1);
    }
}
