package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class FASTORE extends Opcode {
    public FASTORE() {
        super(OpcodeType.ARRAY, Opcode.FASTORE, 1);
    }
}
