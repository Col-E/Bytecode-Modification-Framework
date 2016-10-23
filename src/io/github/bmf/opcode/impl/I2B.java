package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class I2B extends Opcode {
    public I2B() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.I2B, 1);
    }
}
