package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LASTORE extends Opcode {
    public LASTORE() {
        super(OpcodeType.ARRAY, Opcode.LASTORE, 1);
    }
}
