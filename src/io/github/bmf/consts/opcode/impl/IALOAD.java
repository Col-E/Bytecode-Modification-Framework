package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class IALOAD extends Opcode {
    public IALOAD() {
        super(OpcodeType.ARRAY, Opcode.IALOAD, 1);
    }
}
