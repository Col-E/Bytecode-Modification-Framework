package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class AALOAD extends Opcode {
    public AALOAD() {
        super(OpcodeType.ARRAY, Opcode.AALOAD, 1);
    }
}
