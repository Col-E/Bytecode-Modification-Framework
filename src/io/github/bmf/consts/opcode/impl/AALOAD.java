package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class AALOAD extends Opcode {
    public AALOAD() {
        super(OpcodeType.ARRAY, Opcode.AALOAD, 1);
    }
}
