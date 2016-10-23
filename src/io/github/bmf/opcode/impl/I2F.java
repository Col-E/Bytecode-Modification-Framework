package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class I2F extends Opcode {
    public I2F() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.I2F, 1);
    }
}
