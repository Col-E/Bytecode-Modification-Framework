package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class BALOAD extends Opcode {
    public BALOAD() {
        super(OpcodeType.ARRAY, Opcode.BALOAD, 1);
    }
}
