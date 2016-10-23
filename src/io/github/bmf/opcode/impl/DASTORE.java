package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class DASTORE extends Opcode {
    public DASTORE() {
        super(OpcodeType.ARRAY, Opcode.DASTORE, 1);
    }
}
