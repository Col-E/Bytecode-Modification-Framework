package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class ANEWARRAY extends Opcode {
    public int classIndex;

    public ANEWARRAY(int classIndex) {
        super(OpcodeType.ARRAY, Opcode.ANEWARRAY, 3);
        this.classIndex = classIndex;
    }
}
