package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class INSTANCEOF extends Opcode {
    public int classIndex;

    public INSTANCEOF(int classIndex) {
        super(OpcodeType.TYPE_CONVERSION, Opcode.INSTANCEOF, 3);
        this.classIndex = classIndex;
    }
}
