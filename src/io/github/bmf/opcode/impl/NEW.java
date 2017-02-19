package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class NEW extends Opcode {
    public int classIndex;

    public NEW(int classIndex) {
        super(OpcodeType.ALLOCATION, Opcode.NEW, 3);
        this.classIndex = classIndex;
    }
}
