package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DALOAD extends Opcode {
    public DALOAD() {
        super(OpcodeType.ARRAY, Opcode.DALOAD, 1);
    }
}
