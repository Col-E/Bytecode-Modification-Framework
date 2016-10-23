package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class CALOAD extends Opcode {
    public CALOAD() {
        super(OpcodeType.ARRAY, Opcode.CALOAD, 1);
    }
}
