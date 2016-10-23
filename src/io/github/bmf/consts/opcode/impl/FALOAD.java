package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class FALOAD extends Opcode {
    public FALOAD() {
        super(OpcodeType.ARRAY, Opcode.FALOAD, 1);
    }
}
