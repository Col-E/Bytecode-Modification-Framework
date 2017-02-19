package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractFieldOpcode;
import io.github.bmf.opcode.Opcode;

public class PUTFIELD extends AbstractFieldOpcode {
    public PUTFIELD( int fieldIndex) {
        super(Opcode.PUTFIELD, fieldIndex);
    }
}
