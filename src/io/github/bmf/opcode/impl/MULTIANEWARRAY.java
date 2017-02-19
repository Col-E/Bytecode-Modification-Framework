package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractClassPointer;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class MULTIANEWARRAY extends AbstractClassPointer {
    public int dimensions;

    public MULTIANEWARRAY(int classIndex, int dimensions) {
        super(OpcodeType.ARRAY, Opcode.ANEWARRAY, 3, classIndex);
        this.dimensions = dimensions;
    }
}
