package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class FASTORE extends Opcode {
    public FASTORE() {
        super(OpcodeType.ARRAY, Opcode.FASTORE, 1);
    }
}
