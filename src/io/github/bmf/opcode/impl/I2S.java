package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class I2S extends Opcode {
    public I2S() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.I2S, 1);
    }
}
