package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class CASTORE extends Opcode {
    public CASTORE() {
        super(OpcodeType.ARRAY, Opcode.CASTORE, 1);
    }
}
