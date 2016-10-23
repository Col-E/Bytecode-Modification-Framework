package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class CALOAD extends Opcode {
    public CALOAD() {
        super(OpcodeType.ARRAY, Opcode.CALOAD, 1);
    }
}
