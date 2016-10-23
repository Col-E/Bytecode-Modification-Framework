package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class AASTORE extends Opcode {
    public AASTORE() {
        super(OpcodeType.ARRAY, Opcode.AASTORE, 1);
    }
}
