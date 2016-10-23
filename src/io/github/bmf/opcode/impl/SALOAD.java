package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class SALOAD extends Opcode {
    public SALOAD() {
        super(OpcodeType.ARRAY, Opcode.SALOAD, 1);
    }
}
