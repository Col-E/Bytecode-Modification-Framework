package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class BALOAD extends Opcode {
    public BALOAD() {
        super(OpcodeType.ARRAY, Opcode.BALOAD, 1);
    }
}
