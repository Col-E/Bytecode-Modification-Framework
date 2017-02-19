package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class CHECKCAST extends Opcode {
    public int classIndex;

    public CHECKCAST(int classIndex) {
        super(OpcodeType.TYPE_CONVERSION, Opcode.CHECKCAST, 3);
        this.classIndex = classIndex;
    }
}
