package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LALOAD extends Opcode {
    public LALOAD() {
        super(OpcodeType.ARRAY, Opcode.LALOAD, 1);
    }
}
