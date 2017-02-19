package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractClassPointer;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class NEW extends AbstractClassPointer {

    public NEW(int classIndex) {
        super(OpcodeType.ALLOCATION, Opcode.NEW, 3,classIndex);
    }
}
