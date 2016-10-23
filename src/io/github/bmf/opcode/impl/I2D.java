package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class I2D extends Opcode {
    public I2D() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.I2D, 1);
    }
}
