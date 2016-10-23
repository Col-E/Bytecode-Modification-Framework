package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class I2D extends Opcode {
    public I2D() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.I2D, 1);
    }
}
