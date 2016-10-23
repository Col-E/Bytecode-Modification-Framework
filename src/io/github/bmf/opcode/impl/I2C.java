package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class I2C extends Opcode {
    public I2C() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.I2C, 1);
    }
}
