package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class I2L extends Opcode {
    public I2L() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.I2L, 1);
    }
}
