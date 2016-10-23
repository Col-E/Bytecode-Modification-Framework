package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class I2S extends Opcode {
    public I2S() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.I2S, 1);
    }
}
