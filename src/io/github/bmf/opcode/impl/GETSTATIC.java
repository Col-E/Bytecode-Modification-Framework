package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractFieldOpcode;
import io.github.bmf.opcode.Opcode;

public class GETSTATIC extends AbstractFieldOpcode {
    public GETSTATIC( int fieldIndex) {
        super(Opcode.GETSTATIC, fieldIndex);
    }
}
