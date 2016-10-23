package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class SALOAD extends Opcode {
    public SALOAD() {
        super(OpcodeType.ARRAY, Opcode.SALOAD, 1);
    }
}
