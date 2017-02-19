package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractClassPointer;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class INSTANCEOF extends AbstractClassPointer {

    public INSTANCEOF(int classIndex) {
        super(OpcodeType.TYPE_CONVERSION, Opcode.INSTANCEOF, 3,classIndex);
    }
}
