package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractFieldOpcode;
import io.github.bmf.opcode.Opcode;

public class GETFIELD extends AbstractFieldOpcode {
    public GETFIELD( int fieldIndex) {
        super(Opcode.GETFIELD, fieldIndex);
    }
}
