package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IALOAD extends Opcode {
    public IALOAD() {
        super(OpcodeType.ARRAY, Opcode.IALOAD, 1);
    }
}
