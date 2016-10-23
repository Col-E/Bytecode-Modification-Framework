package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class FALOAD extends Opcode {
    public FALOAD() {
        super(OpcodeType.ARRAY, Opcode.FALOAD, 1);
    }
}
