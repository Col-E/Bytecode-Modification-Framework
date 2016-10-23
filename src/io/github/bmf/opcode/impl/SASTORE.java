package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class SASTORE extends Opcode {
    public SASTORE() {
        super(OpcodeType.ARRAY, Opcode.SASTORE, 1);
    }
}
