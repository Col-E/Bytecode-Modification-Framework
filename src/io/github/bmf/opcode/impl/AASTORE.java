package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class AASTORE extends Opcode {
    public AASTORE() {
        super(OpcodeType.ARRAY, Opcode.AASTORE, 1);
    }
}
