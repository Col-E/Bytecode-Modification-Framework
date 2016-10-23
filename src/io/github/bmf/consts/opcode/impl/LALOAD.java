package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LALOAD extends Opcode {
    public LALOAD() {
        super(OpcodeType.ARRAY, Opcode.LALOAD, 1);
    }
}
