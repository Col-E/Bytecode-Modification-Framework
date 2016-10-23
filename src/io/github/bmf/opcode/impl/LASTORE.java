package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LASTORE extends Opcode {
    public LASTORE() {
        super(OpcodeType.ARRAY, Opcode.LASTORE, 1);
    }
}
