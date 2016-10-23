package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class I2B extends Opcode {
    public I2B() {
        super(OpcodeType.TYPE_CONVERSION, Opcode.I2B, 1);
    }
}
