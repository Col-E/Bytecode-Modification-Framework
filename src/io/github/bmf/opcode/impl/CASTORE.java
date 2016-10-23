package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class CASTORE extends Opcode {
    public CASTORE() {
        super(OpcodeType.ARRAY, Opcode.CASTORE, 1);
    }
}
