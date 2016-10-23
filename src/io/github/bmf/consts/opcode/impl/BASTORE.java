package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class BASTORE extends Opcode {
    public BASTORE() {
        super(OpcodeType.ARRAY, Opcode.BASTORE, 1);
    }
}
