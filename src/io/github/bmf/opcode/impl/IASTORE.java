package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IASTORE extends Opcode {
    public IASTORE() {
        super(OpcodeType.ARRAY, Opcode.IASTORE, 1);
    }
}